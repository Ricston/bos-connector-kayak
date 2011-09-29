/*
 * $Id: KayakConnector.java 1485 2011-09-27 16:49:51Z claude.mamo $
 * --------------------------------------------------------------------------------------
 * Copyright (c) Ricston Ltd.  All rights reserved.  http://www.ricston.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.ricston.bonitasoft.connector.kayak;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.ow2.bonita.connector.core.*;
import org.ow2.bonita.util.*;

public abstract class KayakConnector extends ProcessConnector
{

    private static final String START_SCRIPT = "${";
    private static final String END_SCRIPT = "}";
    private static final String SCRIPT_FILENAME = "/KayakConnector.groovy";

    protected HashMap<String, Object> groovyVariables = new HashMap<String, Object>();
    protected org.w3c.dom.Document results;

    @Override
    protected void executeConnector() throws Exception
    {

        StringBuilder script = new StringBuilder(START_SCRIPT);
        BufferedReader scriptReader = new BufferedReader(new InputStreamReader(
            getClass().getResourceAsStream(SCRIPT_FILENAME)));
        String line;

        while ((line = scriptReader.readLine()) != null)
        {
            script.append(line);
            script.append('\n');
        }

        script.append(END_SCRIPT);
        setGroovyVariables();

        results = (org.w3c.dom.Document) GroovyUtil.evaluate(script.toString(), groovyVariables);
    }

    protected boolean isValidDate(String date)
    {
        boolean isValid = true;
        SimpleDateFormat validDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        validDateFormat.setLenient(false);

        try
        {
            validDateFormat.parse(date);
        }
        catch (Exception e)
        {
            isValid = false;
        }

        return isValid;
    }

    @Override
    abstract protected List<ConnectorError> validateValues();

    abstract protected void setGroovyVariables();

}
