package com.foxcr.ycdevdatabinding.smartrefresh;

public class RefreshEvent {
    public int action;
    public boolean success;
    public boolean noMoreData;

    public static final int AUTO_REFRESH = 0;
    public static final int AUTO_LOAD_MORE = 1;
    public static final int STOP_REFRESH = 2;
    public static final int STOP_LOAD_MORE = 3;
}
