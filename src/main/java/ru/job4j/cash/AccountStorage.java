package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("accounts")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            accounts.putIfAbsent(account.id(), account);
            return accounts.containsKey(account.id());
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            return accounts.replace(account.id(), accounts.get(account.id()), account);
        }
    }

    public boolean delete(int id) {
        synchronized (accounts) {
            return accounts.remove(id, accounts.get(id));
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        if (getById(fromId).isEmpty() && getById(toId).isEmpty()) {
            throw new IllegalArgumentException("Аккаунт с таким ID не существует");
        }
        if (getById(fromId).get().amount() < amount) {
            throw new IllegalArgumentException("Не достаточно средств");
        }
        return update(new Account(fromId, getById(fromId).get().amount() - amount))
                && update(new Account(toId, getById(toId).get().amount() + amount));
    }
}
