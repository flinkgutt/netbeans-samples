
package net.flinkgutt.samples.nodes.api.db;

/**
 *
 * @author Christian
 */
public interface IConnectionService {
    boolean connect(IDatabaseServerSettings settings);
}
