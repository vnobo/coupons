package com.alex.wx.core.wallet.beans;

import java.util.Optional;

public interface WithdrawRepository {
    Optional<Withdraw> findById(long id);
}
