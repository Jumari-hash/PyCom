package com.pycom.app.executor;

import android.content.Context;
import com.chaquo.python.Python;
import com.chaquo.python.PyObject;
import com.chaquo.python.android.AndroidPlatform;

public class PythonExecutorStub {
    private final Context context;

    public PythonExecutorStub(Context context) {
        this.context = context;
    }

    public String execute(String code) {
        try {
            if (!Python.isStarted()) {
                Python.start(new AndroidPlatform(context));
            }

            Python py = Python.getInstance();

            PyObject sys = py.getModule("sys");
            PyObject io = py.getModule("io");
            PyObject output = io.callAttr("StringIO");
            sys.put("stdout", output);
            sys.put("stderr", output);

            py.getModule("builtins").callAttr("exec", code);

            return output.callAttr("getvalue").toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}