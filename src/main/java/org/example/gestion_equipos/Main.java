package org.example.gestion_equipos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestion_equipos");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Equipo equipo = new Equipo();
        equipo.setId(1);
        equipo.setNombre("FC Barcelona");
        equipo.setEstadio("Camp Nou");

        Jugador jugador = new Jugador();
        Jugador jugador2 = new Jugador();
        jugador.setId(1);
        jugador.setNombre("Lionel Messi");
        jugador.setEstatura(1.70f);
        jugador.setPeso(55f);

        jugador2.setId(2);
        jugador2.setNombre("Xavi Hernandez");
        jugador2.setEstatura(1.70f);
        jugador2.setPeso(55f);


        tx.begin();  //REMOVE
            if(em.find(Equipo.class, equipo.getId()) != null) {
                em.remove(em.find(Equipo.class, equipo.getId()));
            }
            if(em.find(Jugador.class, jugador.getId()) != null) {
                em.remove(em.find(Jugador.class, jugador.getId()));
            }
            if(em.find(Jugador.class, jugador2.getId()) != null) {
                em.remove(em.find(Jugador.class, jugador2.getId()));
            }
        tx.commit();


        tx.begin();  //INSERT
            em.persist(equipo);
            jugador.setIdEquipo(equipo);
            jugador2.setIdEquipo(equipo);
            em.persist(jugador);
            em.persist(jugador2);
        tx.commit();

        tx.begin();  //UPDATE
            equipo.setNombre("Real Madrid");
            equipo.setEstadio("Santiago Bernabeu");
            em.merge(equipo);
            jugador.setNombre("Cristiano Ronaldo");
            jugador.setEstatura(1.90f);
            jugador.setPeso(80f);
            jugador.setIdEquipo(equipo);
            jugador2.setNombre("Luka Modric");
            jugador2.setEstatura(1.70f);
            jugador2.setPeso(75f);
            jugador2.setIdEquipo(equipo);
            em.merge(jugador);
            em.merge(jugador2);
        tx.commit();

        tx.begin(); //SELECT
            System.out.println(em.find(Equipo.class, equipo.getId()));

        tx.commit();

        tx.begin(); //SELECT ALL
            List<Jugador> listaJugadores = em.createQuery("from Jugador ").getResultList();
            for(Jugador jugador1 : listaJugadores){
                System.out.println(jugador1);
            }
    }
}
