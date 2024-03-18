/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package program;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SzabóRoland(SZF_2023
 */
public class Bab {
    public static class Jatek{
        // 0: közös
        // 1: Gép
        // 2: Játékos
        static int[] babok = new int[3];
        static int kijon = 2;
        static int kinyert = 0;
        // <editor-fold defaultstate="collapsed" desc="Event implmentation">
        private static Set<EI.BBListener> listeners = new HashSet();

        public static void addListener(EI.BBListener listener) {
            listeners.add(listener);
            listener.adatFrissites();
        }

        public static void removeListener(EI.BBListener listener) {
            listeners.remove(listener);
            broadcast();
        }

        private static void broadcast() {
            if(listeners.stream().count() > 0){ 
                listeners.forEach(x -> x.adatFrissites());
                if(babok[0]  <= 0){
                    kinyert = kijon;
                    listeners.forEach(x -> x.valakiNyert());
                }
            }
        }
        // </editor-fold>
        
        
        public static void Restart(){
            kijon = 2;
            kinyert = 0;
            babok[0] = 20;
            for(int i = 1; i < babok.length; i++){
                babok[i] = 0;
            }
            broadcast();
        }
        
        public static int getKijon() { return kijon; }
        public static int getKiNyert() { return kinyert; }
        public static int getJatBabszam(int jatekos) { return jatekos > 0 && jatekos < babok.length ? babok[jatekos] : 0; }
        public static int getKozBabszam() { return babok[0]; }
        
        public static void actionJatekosLep(int mennyi){
            if(kijon == 2 && kinyert == 0 && mennyi > 0 && mennyi < 3 && babok[0] - mennyi >= 0){
                babok[0] -= mennyi;
                babok[2] += mennyi;
                broadcast();
                kijon = 1;
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException ex) {

                }
                actionGepLep();
            }
        }
        private static void actionGepLep(){
            if(kijon == 1 && kinyert == 0){
                int elvesz = (int)((Math.random() *2)+1);
                int elv = babok[0] - elvesz >= 0 ? elvesz : babok[0];
                babok[0] -= elv;
                babok[1] += elv;
                broadcast();
                kijon++;
            }
        }
    }
}
