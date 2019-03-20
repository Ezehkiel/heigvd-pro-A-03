package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.ConnexionBd;

import java.sql.Connection;


public class ConnexionDbTest {
    public static void main(String[] args){
        Connection Con =  ConnexionBd.connecterBd();
    }
}
