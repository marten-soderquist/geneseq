package se.allfader.geneseq.infrastructure.persistence.jpa;

import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;

public class ErrorMapper {
    //TODO Map Persistence error to useful domain concepts
//    ConstraintViolationException
//    public static RuntimeException mapPersistenceException(PersistenceException e) {
//        switch (e){
//
//
//            default:
//                return new RuntimeException("An error occurred during persistence", e);
//        }
//    }
}
