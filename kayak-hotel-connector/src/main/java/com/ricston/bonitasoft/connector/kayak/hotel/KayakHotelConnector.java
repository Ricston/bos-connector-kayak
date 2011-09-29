/*
 * $Id: KayakHotelConnector.java 1485 2011-09-27 16:49:51Z claude.mamo $
 * --------------------------------------------------------------------------------------
 * Copyright (c) Ricston Ltd.  All rights reserved.  http://www.ricston.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.ricston.bonitasoft.connector.kayak.hotel;

import com.ricston.bonitasoft.connector.kayak.*;

import java.util.*;
import org.ow2.bonita.connector.core.ConnectorError;

public class KayakHotelConnector extends KayakConnector
{

    private Long searchPollingInterval;
    private Long guests;
    private Long rooms;

    private String developerKey;
    private String city;
    private String checkinDate;
    private String checkoutDate;

    protected List<ConnectorError> validateValues()
    {
        ArrayList<ConnectorError> errorList = new ArrayList<ConnectorError>();
        ArrayList<ConnectorError> returnList = null;

        if (!isValidDate(checkinDate))
            errorList.add(new ConnectorError("checkinDate", new Exception(
                "Checkin date must be in mm/dd/yyyy format")));

        if (!isValidDate(checkoutDate))
            errorList.add(new ConnectorError("checkoutDate", new Exception(
                "Checkout date must be in mm/dd/yyyy format")));

        if (guests < 1 || guests > 6)
            errorList.add(new ConnectorError("guests", new Exception("Guests must be between 1 and 6")));

        if (rooms < 1 || rooms > 3)
            errorList.add(new ConnectorError("rooms", new Exception("Rooms must be between 1 and 3")));

        if (errorList.size() > 0) returnList = errorList;

        return returnList;
    }

    protected void setGroovyVariables()
    {
        groovyVariables.put("searchPollingInterval", searchPollingInterval);
        groovyVariables.put("developerKey", developerKey);
        groovyVariables.put("checkinDate", checkinDate);
        groovyVariables.put("checkoutDate", checkoutDate);
        groovyVariables.put("guests", guests);
        groovyVariables.put("city", city);
        groovyVariables.put("rooms", rooms);
        groovyVariables.put("searchType", "h");
    }

    public void setDeveloperKey(String developerKey)
    {
        this.developerKey = developerKey;
    }

    public void setSearchPollingInterval(Long searchPollingInterval)
    {
        this.searchPollingInterval = searchPollingInterval;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setCheckinDate(String checkinDate)
    {
        this.checkinDate = checkinDate;
    }

    public void setCheckoutDate(String checkoutDate)
    {
        this.checkoutDate = checkoutDate;
    }

    public void setGuests(Long guests)
    {
        this.guests = guests;
    }

    public void setRooms(Long rooms)
    {
        this.rooms = rooms;
    }

    public org.w3c.dom.Document getResults()
    {
        return this.results;
    }

}
