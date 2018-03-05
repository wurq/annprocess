package com.arch.annomodule;

/**
 *
 */

public interface Constants {

    String ANNO_FACADE_PKG = "com.arch.annomodule";


    ///////////////////////////////////////////////////////////////////////////
    // Options of processor
    ///////////////////////////////////////////////////////////////////////////
    String KEY_HOST_NAME = "host";

    String ANNOTATION_TYPE_ROUTE_NODE = ANNO_FACADE_PKG + ".RouteNode";
    String ANNOTATION_TYPE_ROUTER = ANNO_FACADE_PKG + ".Router";
    String ANNOTATION_TYPE_AUTOWIRED = ANNO_FACADE_PKG + ".annotation.Autowired";

    String PREFIX_OF_LOGGER = "[Anno-Process]-- ";


    // System interface
    String ACTIVITY = "android.app.Activity";
    String FRAGMENT = "android.app.Fragment";
    String FRAGMENT_V4 = "android.support.v4.app.Fragment";
    String SERVICE = "android.app.Service";
    String PARCELABLE = "android.os.Parcelable";

    // Java type
    String LANG = "java.lang";
    String BYTE = LANG + ".Byte";
    String SHORT = LANG + ".Short";
    String INTEGER = LANG + ".Integer";
    String LONG = LANG + ".Long";
    String FLOAT = LANG + ".Float";
    String DOUBEL = LANG + ".Double";
    String BOOLEAN = LANG + ".Boolean";
    String STRING = LANG + ".String";

//    String ISYRINGE = "com.luojilab.component.componentlib.router.ISyringe";
//
//    String JSON_SERVICE = "com.luojilab.component.componentlib.service.JsonService";
//
//    String BASECOMPROUTER = "com.luojilab.component.componentlib.router.ui.BaseCompRouter";


}
