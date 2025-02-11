package com.project.fsneaker.models;

import com.project.fsneaker.services.IProductRedisService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class ProductListener {

    private final IProductRedisService productRedisService;

    private static final Logger logger = LoggerFactory.getLogger(ProductListener.class);

    @PrePersist
    public void prePersist(Product product){
        logger.info("prePersist");
    }

    @PostPersist    // Save = persist
    public void postPersist(Product product){
        // Update redis cache
        logger.info("postPersist");
        productRedisService.clear();
    }

    @PreUpdate
    public void preUpdate(Product product){
        logger.info("preUpdate");
    }

    @PostUpdate
    public void postUpdate(Product product){
        // Update redis cache
        logger.info("postUpdate");
        productRedisService.clear();
    }

    @PreRemove
    public void preRemove(Product product){
        logger.info("preRemove");
    }

    @PostRemove
    public void postRemove(Product product){
        // Update redis cache
        logger.info("postRemove");
        productRedisService.clear();
    }

}
