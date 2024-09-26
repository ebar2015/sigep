/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.sistema;

import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
 * @author Sergio Martínez
 * @version 1.0
 * @Class Clase que calcula el dígito de verificación a partir del NIT
 * @Project SIGEPII
 * @Company ADA S.A
 * @Module Web
 * @Fecha: Viernes 20 de Julio de 2018
 */
public abstract class DigitoVerificacion {

    /**
     * Método que calcula el dígito de verificación a partir del NIT
     *
     * @param nit El nit sobre el que se desea calcular el dígito de
     * verificación
     * @return El dígito de verificación calculado
     * @throws co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException
     */
    public static String calcularDigitoVerificacion(String nit) throws SIGEP2SistemaException {
        int[] pseudoPrimos = {3, 7, 13, 17, 19, 23, 29, 37, 41, 43, 47, 53};
        if (nit == null || nit.isEmpty() || nit.length() > pseudoPrimos.length) {
            throw new SIGEP2SistemaException("Error en el nit enviado: " + nit);
        }
        int suma = 0;
        int indice = 0;
        for (int i = nit.length() - 1; i >= 0; i--) {
            String digito = nit.substring(i, i + 1);
            try {
                int digitoInt = Integer.parseInt(digito);
                suma += (digitoInt * pseudoPrimos[indice]);
                indice++;
            } catch (NumberFormatException ex) {
            }
        }
        int mod = suma % 11;
        if (mod == 0 || mod == 1) {
            return "" + mod;
        } else {
            int dv = 11 - mod;
            return "" + dv;
        }
    }
}
