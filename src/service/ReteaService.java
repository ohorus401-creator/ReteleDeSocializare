package service;

import domain.Prietenie;
import domain.Tuple;
import domain.Utilizator;
import repository.Repository;

import java.util.*;

/**
 * Service that computes information about the communities of the network.
 */
public class ReteaService {
    private final Repository<Long, Utilizator> repoUser;
    private final Repository<Tuple<Long, Long>, Prietenie> repoPrietenie;

    /**
     * Creates the service.
     *
     * @param repoUser repository of the users
     * @param repoPrietenie repository of the friendships
     */
    public ReteaService(Repository<Long, Utilizator> repoUser, Repository<Tuple<Long, Long>, Prietenie> repoPrietenie) {
        this.repoUser = repoUser;
        this.repoPrietenie = repoPrietenie;
    }

    /**
     * Builds a graph with all the connections between users ( friendships )
     * @return a map with users ids as keys and a list of its friends ids as values
     */
    private Map<Long, List<Long>> construiesteGraf(){
        Map<Long, List<Long>> graf = new HashMap<>();

        for ( Utilizator user : repoUser.findAll()){
            graf.put(user.getId(), new ArrayList<>());
        }

        for ( Prietenie pr : repoPrietenie.findAll()){
            Long user1ID = pr.getIdUser1();
            Long user2ID = pr.getIdUser2();

            graf.get(user1ID).add(user2ID);
            graf.get(user2ID).add(user1ID);
        }

        return graf;
    }

    /**
     * Dfs algorithm
     */
    private void dfs(Long nod, Map<Long, List<Long>> graf, Set<Long> vizitati){
        vizitati.add(nod);
        for (Long vecin : graf.get(nod)){
            if (!vizitati.contains(vecin)){
                dfs(vecin, graf, vizitati);
            }
        }
    }

    /**
     * Counts the communities of the network, that is the connected components
     * of the friendship graph.
     *
     * @return the number of communities
     */
    public int numarComunitati(){
        Map<Long, List<Long>> graf = construiesteGraf();
        Set<Long> vizitati = new HashSet<>();
        int contor = 0;
        for( Long id : graf.keySet()){
            if (!vizitati.contains(id)){
                contor++;
                dfs(id,graf,vizitati);
            }
        }
        return contor;
    }

    /**
     * Finds the community with the most users.
     *
     * @return the ids of the users in the largest community,
     *         or null if the network has no users
     */
    public Set<Long> ceaMaiMareComunitate(){
        Map<Integer, Set<Long>> componenteConexe = new HashMap<>();
        Map<Long, List<Long>> graf = construiesteGraf();
        Set<Long> vizitati = new HashSet<>();
        int contor = 0;
        for( Long id : graf.keySet() ){
            if (!vizitati.contains(id)){
                Set<Long> componenta = new HashSet<>();
                dfs(id,graf,componenta);
                vizitati.addAll(componenta);
                contor++;
                componenteConexe.put(contor,componenta);
            }
        }

        int max = 0;
        int key = -1;
        for ( Integer id : componenteConexe.keySet() ){
            Set<Long> comp = componenteConexe.get(id);
            if (comp.size() > max){
                max = comp.size();
                key = id;
            }
        }

        return componenteConexe.get(key);
    }
}