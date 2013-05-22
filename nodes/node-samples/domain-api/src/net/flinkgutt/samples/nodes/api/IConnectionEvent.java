/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.flinkgutt.samples.nodes.api;

/**
 *
 * @author Christian
 */
public interface IConnectionEvent {
    final Integer CONNECT = 1;
    final Integer DISCONNECT = -1;
    
    Integer getEvent();
    void setEvent(final Integer event);
}
