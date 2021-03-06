package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.utilites.VerificationToken;

public interface VerificationTokenDao {

    VerificationToken findByToken(String token);

    VerificationToken create(VerificationToken verificationToken);

    void delete(VerificationToken verificationToken);
}
