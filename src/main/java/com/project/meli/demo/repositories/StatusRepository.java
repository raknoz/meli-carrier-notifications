package com.project.meli.demo.repositories;

import com.project.meli.demo.entities.ShippingStatus;
import com.project.meli.demo.entities.ShippingSubStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class StatusRepository {
    private final Map<ShippingStatus, ShippingSubStatus> nullMapping;

    public StatusRepository() {
        nullMapping = new HashMap<>();
        nullMapping.put(ShippingStatus.HANDLING, ShippingSubStatus.HANDLING_NULL);
        nullMapping.put(ShippingStatus.SHIPPED, ShippingSubStatus.SHIPPED_NULL);
        nullMapping.put(ShippingStatus.DELIVERED, ShippingSubStatus.DELIVERED_NULL);
    }

    /**
     * Repository to get the entity of Shipping status by name.
     *
     * @param statusName name of possible status.
     * @return A Entity to represent the status.
     */
    public ShippingStatus getStatusByName(final String statusName) {
        return ShippingStatus.wrapperValueOf(statusName.toUpperCase());
    }

    /**
     * Repository to get the entity of Shipping sub-status by name.
     *
     * @param subStatusName name of possible sub-status.
     * @return A Entity to represent the sub-status.
     */
    public Optional<ShippingSubStatus> getSubStatusByNameAndStatus(final String subStatusName, final ShippingStatus shippingStatus) {
        Optional<ShippingSubStatus> shippingSubStatus;
        if (StringUtils.isNotBlank(subStatusName)) {
            shippingSubStatus = Optional.of(ShippingSubStatus.wrapperValueOf(subStatusName.toUpperCase()));
        } else {
            shippingSubStatus = Optional.ofNullable(nullMapping.get(shippingStatus));
        }
        return shippingSubStatus;
    }

}
