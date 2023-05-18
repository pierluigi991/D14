package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entities.Evento;
import entities.Partecipazione;
import entities.StatoPartecipazione;
import util.JpaUtil;

public class PartecipazioneDAO {
	private static final Logger logger = LoggerFactory.getLogger(PartecipazioneDAO.class);

	public void save(Partecipazione object) {
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

	public void refresh(Partecipazione object) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {

			em.refresh(object);

		} finally {
			em.close();
		}

	}

	public Partecipazione getById(Long id) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {

			return em.find(Partecipazione.class, id);

		} finally {
			em.close();
		}

	}

	public void delete(Partecipazione object) {
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

	public void getPartecipazioniDaConfermarePerEvento(Evento evento){
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

		try{
			Query q = em.createQuery(
					"SELECT p FROM Partecipazione p WHERE p.evento = :evento AND p.stato = :daConfermare"
			);

			q.setParameter("evento", evento);
			q.setParameter("daConfermare", StatoPartecipazione.DA_CONFERMARE);

			List<Partecipazione> e = q.getResultList();

			for (Partecipazione p : e
			) {
				System.out.println(p);
			}
		} finally {
			em.close();
		}
	}
	//getPartecipazioniDaConfermarePerEvento(Evento evento)

}