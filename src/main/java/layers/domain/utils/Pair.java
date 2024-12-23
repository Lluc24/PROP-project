package layers.domain.utils;

/**
 * Aquesta clase representa una estructura de dades que relaciona dos elements de tipus K1, K2, respectivament.
 * Es una implementació basada en la estructura de C++ pair.
 * Permet fer arrays de pair.
 * @param <K1> Parametre del primer element del pair
 * @param <K2> Parametre del segon element del pair
 */
public class Pair<K1, K2> {
    /**
     * Valor corresponent al primer element del pair, de tipus K1
     */
    public K1 first;

    /**
     * Valor corresponent al segon element del pair, de tipus K1
     */
    public K2 second;

    /**
     * Funcio creadora de un pair
     * @param first Sera el primer element del pair
     * @param second Sera el segon element del pair
     */
    public Pair(K1 first, K2 second) {
        this.first = first;
        this.second = second;
    }
}
