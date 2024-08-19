package com.epam.rd.autotasks.wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Wallet creator. Is used to create a {@linkplain Wallet} instance.
 * <p/>
 * You need to specify your implementation in {@linkplain #wallet(List, PaymentLog)} method.
 */
public final class WalletFactory {

    private WalletFactory() {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates new {@linkplain Wallet} instance and passes {@code accounts} and {@code log} to it.
     * <p/>
     * You must return your implementation here.
     *
     * @param accounts which will be used for payments
     * @param log which will be used to log payments
     * @return new {@linkplain Wallet} instance
     */
    public static Wallet wallet(List<Account> accounts, PaymentLog log) {

        return new Wallet() {

            ReentrantLock lock = new ReentrantLock();

            @Override
            public void pay(String recipient, long amount) throws ShortageOfMoneyException {

                lock.lock();
                    try{
                       boolean found = false;

                        for (int i = 0; i < accounts.size(); i++) {

                                if (accounts.get(i).balance() >= amount) {
                                   found = true;
                                       accounts.get(i).pay(amount);
                                       log.add(accounts.get(i), recipient, amount);
                                    break;
                                }
                        }

                        if(!found)
                            throw new ShortageOfMoneyException(recipient, amount);

                    } finally {
                        lock.unlock();
                    }

                }
        };
    }
}
