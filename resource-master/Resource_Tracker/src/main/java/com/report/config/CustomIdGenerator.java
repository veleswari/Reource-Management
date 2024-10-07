package com.report.config;

import jakarta.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomIdGenerator implements IdentifierGenerator {

    private static final String KEYWORD = "hds-";

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String prefix = KEYWORD;
        Query query = session.createNativeQuery("SELECT nextval('my_sequence')");
        Long seqValue = ((Number) query.getSingleResult()).longValue();
        return prefix + seqValue;
    }
}
