/*
 * $Id: KayakFlightConnector.java 1477 2011-09-26 16:26:13Z claude.mamo $
 * --------------------------------------------------------------------------------------
 * Copyright (c) Ricston Ltd  All rights reserved.  http://www.ricston.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file. 
 */

package com.ricston.bonitasoft.connector.kayak.flight;

import java.util.*;
import org.ow2.bonita.connector.core.ConnectorError;

import com.ricston.bonitasoft.connector.kayak.*;

public class KayakFlightConnector extends KayakConnector
{

    private Long searchPollingInterval;
    private Long travellers;

    private String cabin;
    private String returnTime;
    private String departureDate;
    private String oneway;
    private String returnDate;
    private String destination;
    private String origin;
    private String departureTime;
    private String developerKey;

    protected List<ConnectorError> validateValues()
    {

        ArrayList<ConnectorError> errorList = new ArrayList<ConnectorError>();
        ArrayList<ConnectorError> returnList = null;

        if (!isValidDate(departureDate))
            errorList.add(new ConnectorError("departureDate", new Exception(
                "Departure date must be in 'MM/DD/YYYY' format")));

        if (!isValidDate(returnDate))
            errorList.add(new ConnectorError("returnDate", new Exception(
                "Return date must be in 'MM/DD/YYYY' format")));

        if (origin.length() != 3)
            errorList.add(new ConnectorError("origin", new Exception(
                "Origin must be three-letter airport code")));

        if (destination.length() != 3)
            errorList.add(new ConnectorError("destination", new Exception(
                "Destination must be three-letter airport code")));

        if (travellers < 1 || travellers > 8)
            errorList.add(new ConnectorError("travellers",
                new Exception("Travellers must be between 1 and 8")));

        if ((oneway.compareToIgnoreCase("Y") != 0) && (oneway.compareToIgnoreCase("N") != 0))
            errorList.add(new ConnectorError("oneway", new Exception("Oneway must be set to 'y' or 'n'")));

        if (!isValidTime(departureTime))
            errorList.add(new ConnectorError("departureTime", new Exception(
                "Departure time must be set to 'a', 'r', 'm', '12', 'n', 'e' or 'l'")));

        if (!isValidTime(returnTime))
            errorList.add(new ConnectorError("returnTime", new Exception(
                "Return time must be set to 'a', 'r', 'm', '12', 'n', 'e' or 'l'")));

        if ((cabin.compareToIgnoreCase("f") != 0) && (cabin.compareToIgnoreCase("b") != 0)
            && (cabin.compareToIgnoreCase("e") != 0))
            errorList.add(new ConnectorError("cabin", new Exception("Cabin must be set to 'f', 'b', or 'e'")));

        if (errorList.size() > 0) returnList = errorList;

        return returnList;
    }

    private boolean isValidTime(String time)
    {
        if ((time.compareToIgnoreCase("a") != 0) && (time.compareToIgnoreCase("r") != 0)
            && (time.compareToIgnoreCase("m") != 0) && (time.compareToIgnoreCase("12") != 0)
            && (time.compareToIgnoreCase("n") != 0) && (time.compareToIgnoreCase("e") != 0)
            && (time.compareToIgnoreCase("l") != 0))
            return false;
        else
            return true;
    }

    protected void setGroovyVariables()
    {
        groovyVariables.put("searchPollingInterval", searchPollingInterval);
        groovyVariables.put("developerKey", developerKey);
        groovyVariables.put("departureDate", departureDate);
        groovyVariables.put("returnDate", returnDate);
        groovyVariables.put("returnTime", returnTime);
        groovyVariables.put("departureTime", departureTime);
        groovyVariables.put("cabin", cabin);
        groovyVariables.put("oneway", oneway);
        groovyVariables.put("travellers", travellers);
        groovyVariables.put("destination", destination);
        groovyVariables.put("origin", origin);
        groovyVariables.put("searchType", "f");
    }

    public void setCabin(String cabin)
    {
        this.cabin = cabin;
    }

    public void setReturnTime(String returnTime)
    {
        this.returnTime = returnTime;
    }

    public void setDepartureDate(String departureDate)
    {
        this.departureDate = departureDate;
    }

    public void setOneway(String oneway)
    {
        this.oneway = oneway;
    }

    public void setReturnDate(String returnDate)
    {
        this.returnDate = returnDate;
    }

    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public void setDepartureTime(String departureTime)
    {
        this.departureTime = departureTime;
    }

    public void setDeveloperKey(String developerKey)
    {
        this.developerKey = developerKey;
    }

    public void setSearchPollingInterval(Long searchPollingInterval)
    {
        this.searchPollingInterval = searchPollingInterval;
    }

    public void setTravellers(Long travellers)
    {
        this.travellers = travellers;
    }

    public org.w3c.dom.Document getResults()
    {
        return results;
    }

}
