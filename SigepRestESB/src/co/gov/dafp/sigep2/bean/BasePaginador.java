/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.bean;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
 * Se crea esta clase como base para todas en la que se desee poner paginador
 * @author Sergio Andrés Martínez
 */
public class BasePaginador extends ErrorMensajes{

    private Integer limitInit;

    private Integer limitEnd;

    /**
     * @return the limitInit
     */
    public Integer getLimitInit() {
        return limitInit;
    }

    /**
     * @param limitInit the limitInit to set
     */
    public void setLimitInit(Integer limitInit) {
        this.limitInit = limitInit;
    }

    /**
     * @return the limitEnd
     */
    public Integer getLimitEnd() {
        return limitEnd;
    }

    /**
     * @param limitEnd the limitEnd to set
     */
    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }
}
