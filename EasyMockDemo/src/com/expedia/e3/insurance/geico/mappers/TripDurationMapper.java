/**
 * Copyright 2012 Expedia, Inc. All rights reserved.
 * EXPEDIA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.expedia.e3.geico.mappers;

import com.expedia.e3.geico.builder.ValueTypeBuilder;
import com.expedia.e3.geico.endpoint.GetInsuranceOptionsV3Request;
import com.expedia.e3.insurance.basetypes.v1.TripInfoType;
import com.expedia.e3.insurance.expressions.v1.ValueType;
import com.expedia.e3.insurance.shoppingservice.messages.v1.GetInsuranceOptionsRequestType;
import com.expedia.e3.insurance.shoppingservice.messages.v1.GetInsuranceOptionsV2RequestType;
import com.expedia.e3.insurance.shoppingservice.messages.v2.CarInsuranceProductRequestType;
import com.expedia.e3.insurance.shoppingservice.messages.v2.FlightInsuranceProductRequestType;
import com.expedia.e3.insurance.shoppingservice.messages.v2.FlightLegInfoType;
import com.expedia.e3.insurance.shoppingservice.messages.v2.HotelInsuranceProductRequestType;
import com.expedia.e3.insurance.shoppingservice.messages.v2.InsuranceProductRequestType;
import com.expedia.e3.insurance.types.AttributeKey;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.List;

/**
 * TripDurationMapper.java
 *
 * @author <a href="mailto:v-fetang@expedia.com">Feigo</a>
 */
public class TripDurationMapper implements RequestContextMapper<ValueType> {

    Logger logger = Logger.getLogger(TripDurationMapper.class);
    @Override
    public ValueType mapRequestToContext(GetInsuranceOptionsRequestType request) {

        List<TripInfoType> tripInfoList = request.getTripInfoList();
        int stayDuration = getStayDuration(tripInfoList.get(0).getDepartureTime(), tripInfoList.get(tripInfoList.size() - 1).getReturnTime());
        return new ValueTypeBuilder().with(stayDuration).build();
    }

    @Override
    public ValueType mapRequestToContext(GetInsuranceOptionsV2RequestType request) {

        DateTime tripStartDate = request.getTripDetails().getTripStartDate();
        DateTime tripEndDate = request.getTripDetails().getTripEndDate();
        int stayDuration = getStayDuration(tripStartDate, tripEndDate);
        return new ValueTypeBuilder().with(stayDuration).build();
    }

    @Override
    public ValueType mapRequestToContext(GetInsuranceOptionsV3Request request) {
        int tripDuration = 0;
        
        //The "least" end date
        DateTime tripEndDate = new DateTime(0L);
        //The "largest" start date
        DateTime tripStartDate = new DateTime();
        tripStartDate = tripStartDate.plusYears(1000);
        
        for(InsuranceProductRequestType insuranceProductRequest : request.getInsuranceProductRequestList().getInsuranceProductRequest())
        {
            DateTime curTripStartDate = null;
            DateTime curTripEndDate = null;
            if(insuranceProductRequest instanceof HotelInsuranceProductRequestType) 
            {
                HotelInsuranceProductRequestType hotelInsuranceProductRequest = (HotelInsuranceProductRequestType)insuranceProductRequest;
                curTripStartDate = hotelInsuranceProductRequest.getHotelInfo().getCheckInDate();
                curTripEndDate = hotelInsuranceProductRequest.getHotelInfo().getCheckOutDate();
                
            }else if(insuranceProductRequest instanceof FlightInsuranceProductRequestType){
                FlightInsuranceProductRequestType flightInsuranceProductRequest = (FlightInsuranceProductRequestType)insuranceProductRequest;
                List<FlightLegInfoType> flightLegInfoList = flightInsuranceProductRequest.getFlightInfo().getFlightLegInfoList().getFlightLegInfo();
                int size = flightLegInfoList.size();
                curTripStartDate = flightLegInfoList.get(0).getDepartureTime();
                curTripEndDate = flightLegInfoList.get(size-1).getArrivalTime();
                
            }else if(insuranceProductRequest instanceof CarInsuranceProductRequestType){
                CarInsuranceProductRequestType carInsuranceProductRequest = (CarInsuranceProductRequestType)insuranceProductRequest;
                curTripStartDate = carInsuranceProductRequest.getCarInfo().getPickupTime();
                curTripEndDate = carInsuranceProductRequest.getCarInfo().getDropoffTime();
            }
            
            //update the start date and end date to find the least start date and largest end date
            if (curTripStartDate.isBefore(tripStartDate)){
                tripStartDate = curTripStartDate;
            }
            if (curTripEndDate.isAfter(tripEndDate)){
                tripEndDate = curTripEndDate;
            }
        }
        //largest end date minus the least start date
        tripDuration  =  getStayDuration(tripStartDate, tripEndDate);
        logger.info("the trip start is "+tripStartDate.toString());
        logger.info("the trip end is "+tripEndDate.toString());
        logger.info("the trip duration is "+tripDuration);
        return new ValueTypeBuilder().with(tripDuration).build();
    }

    @Override
    public String getKey() {
        return AttributeKey.TRIP_DURATIONS.getKeyValue();
    }

    /**
     * Return the number of days from departure time to return time.
     *
     * @param departureTime, returnTime
     * @return duration
     */
    private int getStayDuration(DateTime departureTime, DateTime returnTime) {

        Days daysBetween = Days.daysBetween(departureTime.toDateMidnight(), returnTime.plusDays(1));
        return daysBetween.getDays();

    }
}
