package com.project.meli.demo.services;

import com.project.meli.demo.dtos.ShippingStatisticsResponseDTO;
import com.project.meli.demo.entities.ShippingStatisticEntity;
import com.project.meli.demo.exceptions.BadRequestException;
import com.project.meli.demo.repositories.ShippingStatisticRepository;
import com.project.meli.demo.util.MapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.project.meli.demo.util.DateUtils.parseStringToLocalDateTime;
import static java.util.Objects.nonNull;

@Service
public class ShippingStatisticsService {
    private final Logger logger = LoggerFactory.getLogger(ShippingStatisticsService.class);
    private final ShippingStatisticRepository shippingStatisticRepository;

    public ShippingStatisticsService(final ShippingStatisticRepository shippingStatisticRepository) {
        this.shippingStatisticRepository = shippingStatisticRepository;
    }

    /**
     * Method to return a list of Shipping historical records in pages.
     *
     * @param fromDateStr Initial date to filter the report.
     * @param toDateStr   Final date to filter the report.
     * @return Object with results of Statistics
     */
    public ShippingStatisticsResponseDTO getStatisticsByDate(final String fromDateStr, final String toDateStr) {
        logger.info("Process statistics from data base");
        final LocalDate fromDate = parseStringToLocalDateTime(fromDateStr);
        final LocalDate toDate = parseStringToLocalDateTime(toDateStr);
        if(nonNull(fromDate) && nonNull(toDate) && toDate.isBefore(fromDate)){
            throw new BadRequestException("Date from is after than date to!");
        }
        final ShippingStatisticEntity response = shippingStatisticRepository.getStatisticsByDate(fromDate, toDate);
        return MapperUtils.convertEntityToDto(response, fromDate, toDate);
    }
}
