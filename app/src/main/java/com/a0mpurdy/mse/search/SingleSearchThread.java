package com.a0mpurdy.mse.search;

import com.a0mpurdy.mse_core.log.LogRow;
import com.a0mpurdy.mse.search.results.IResult;

import java.util.ArrayList;

/**
 * Created by Michael on 17/11/2015.
 */
public abstract class SingleSearchThread extends Thread {

    abstract ArrayList<LogRow> getLog();

    abstract ArrayList<IResult> getResults();

    abstract int getNumberOfResults();

}
