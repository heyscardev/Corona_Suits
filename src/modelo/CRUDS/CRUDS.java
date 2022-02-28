/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.CRUDS;

import java.util.ArrayList;

/**
 *
 * @author heyscar
 */
public interface CRUDS {
    public  boolean CREATE();
    public  boolean UPDATE();
    public  Object READ(Object O);
    public  boolean DELETE();
    public  ArrayList READALL();
}
