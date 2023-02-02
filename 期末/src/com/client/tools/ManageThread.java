package com.client.tools;

import java.util.Hashtable;

/**
 * 县城管理
 */
public class ManageThread {

    private static Hashtable<String,ClientToServerThread> threads = new Hashtable<>();

    public static void addThread(String uid,ClientToServerThread thread){
        threads.put(uid,thread);
    }

    public static ClientToServerThread getThread(String uid){
        return threads.get(uid);
    }

    public static void removeThread(String uid){
        threads.remove(uid);
    }
}