package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entities.Concerto;
import entities.Evento;
import entities.GaraDiAtletica;
import entities.GenereConcerto;
import entities.PartitaDiCalcio;
import entities.Persona;
import util.JpaUtil;

public class EventoDAO {
	private static final Logger logger = LoggerFactory.getLogger(EventoDAO.class);

	public void save(Evento object) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {

			EntityTransaction transaction = em.getTransaction();
			transaction.begin();

			em.persist(object);

			transaction.commit();
		} catch (Exception ex) {
			em.getTransaction().rollback();

			logger.error("Error saving object: " + object.getClass().getSimpleName(), ex);
			throw ex;

		} finally {
			em.close();
		}

	}

	public void refresh(Evento object) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {

			em.refresh(object);

		} finally {
			em.close();
		}

	}

	public Evento getById(Long id) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {

			return em.find(Evento.class, id);

		} finally {
			em.close();
		}

	}

	public void delete(Evento object) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {

			EntityTransaction transaction = em.getTransaction();
			transaction.begin();

			em.remove(object);

			transaction.commit();
		} catch (Exception ex) {
			em.getTransaction().rollback();
			logger.error("Error deleting object: " + object.getClass().getSimpleName(), ex);
			throw ex;

		} finally {
			em.close();
		}

	}

	public List<Concerto> getConcertiInStreaming(Boolean inStreaming) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {

			Query query = em.createNamedQuery("concertiInStreaming");

			query.setParameter("streaming", inStreaming);
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Concerto> getConcertiPerGenere(List<GenereConcerto> listaGeneri) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {

			Query query = em.createNamedQuery("concertiPerGenere");

			query.setParameter("listagenere", listaGeneri);
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public void getPartiteVinteInCasa(){
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

		try{
			Query q = em.createQuery(
					"SELECT p FROM PartitaDiCalcio p WHERE p.squadraVincente = p.squadraDiCasa"
			);
			List<PartitaDiCalcio> r = q.getResultList();

			for (PartitaDiCalcio p : r
			) {
				System.out.println(p);
			}
		} finally {
			em.close();
		}
	}

	public void getPartiteVinteInTrasferta(){
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

		try{
			Query q = em.createQuery(
					"SELECT p FROM PartitaDiCalcio p WHERE p.squadraVincente = p.squadraOspite"
			);
			List<PartitaDiCalcio> r = q.getResultList();

			for (PartitaDiCalcio p : r
			) {
				System.out.println(p);
			}
		} finally {
			em.close();
		}
	}

	public void getPartitePareggiate(){
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

		try{
			Query q = em.createQuery(
					"SELECT p FROM PartitaDiCalcio p WHERE p.squadraVincente IS NULL"
			);
			List<PartitaDiCalcio> r = q.getResultList();

			for (PartitaDiCalcio p : r
			) {
				System.out.println(p);
			}
		} finally {
			em.close();
		}
	}

	public void getGareDiAtleticaPerVincitore(Persona vincitore){
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

		try{
			Query q = em.createQuery(
					"SELECT g FROM GaraDiAtletica g WHERE g.vincitore = :vincitore"
			);
			q.setParameter("vincitore", vincitore);

			List<GaraDiAtletica> r = q.getResultList();

			for (GaraDiAtletica g : r
			) {
				System.out.println(g);
			}
		} finally {
			em.close();
		}
	}

	public void getGareDiAtleticaPerPartecipante(Persona partecipante){
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

		try{
			Query q = em.createQuery(
					"SELECT g FROM GaraDiAtletica g WHERE :partecipante MEMBER OF g.setAtleti "
			);
			q.setParameter("partecipante", partecipante);

			List<GaraDiAtletica> r = q.getResultList();

			for (GaraDiAtletica g : r
			) {
				System.out.println(g);
			}
		} finally {
			em.close();
		}
	}


	public void getEventiSoldOut(){
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

		try{
			Query q = em.createQuery(
					"SELECT e FROM Evento e WHERE SIZE(e.setPartecipazioni) = e.numeroMassimoPartecipanti"
			);

			List<Evento> e = q.getResultList();

			for (Evento ev : e
			) {
				System.out.println(ev);
			}
		} finally {
			em.close();
		}
	}

	public void getEventiPerInvitato(String invitato){
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

		try{
			Query q = em.createQuery(
					"SELECT e FROM Evento e WHERE EXISTS(SELECT p FROM e.setPartecipazione p WHERE p.persona LIKE :invitato)"
			);

			q.setParameter("invitato", invitato);

			List<Evento> e = q.getResultList();

			for (Evento ev : e
			) {
				System.out.println(ev);
			}
		} finally {
			em.close();
		}
	}


}
