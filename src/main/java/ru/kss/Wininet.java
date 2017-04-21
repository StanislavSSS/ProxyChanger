package ru.kss;

import com.sun.jna.Native;
import com.sun.jna.win32.*;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32;
import com.sun.jna.win32.StdCallLibrary;

import java.util.Map;

public interface Wininet extends StdCallLibrary {
    int INTERNET_OPTION_SETTINGS_CHANGED = 39;

    Wininet INSTANCE = (Wininet)Native.loadLibrary("Wininet", Wininet.class,
            W32APIOptions.DEFAULT_OPTIONS);

    boolean InternetSetOptionW(int unused, int dwOption, Pointer lpBuffer,
                               int dwBufferLength);
}