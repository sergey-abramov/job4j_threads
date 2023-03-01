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
        boolean rsl = false;
        synchronized (accounts) {
            accounts.putIfAbsent(account.id(), account);
            if (accounts.containsKey(account.id())) {
                rsl = true;
            }
        }
        return rsl;
    }

    public boolean update(Account account) {
        boolean rsl = false;
        synchronized (accounts) {
            accounts.put(account.id(), account);
            if (accounts.containsValue(account)) {
                rsl = true;
            }
        }
        return rsl;
    }

    public boolean delete(int id) {
        boolean rsl = false;
        synchronized (accounts) {
            accounts.remove(id);
            if (!accounts.containsKey(id)) {
                rsl = true;
            }
        }
        return rsl;
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        synchronized (accounts) {
            Account first = new Account(fromId, accounts.get(fromId).amount() - amount);
            Account second = new Account(toId, accounts.get(toId).amount() + amount);
            accounts.replace(fromId, first);
            accounts.replace(toId, second);
            if (accounts.get(fromId).amount() == first.amount()
                    && accounts.get(toId).amount() == second.amount()) {
                rsl = true;
            }
        }
        return rsl;
    }
}
