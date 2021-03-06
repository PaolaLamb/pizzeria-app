package fr.pizzeria.admin.metier;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.pizzeria.admin.exception.StockageException;
import fr.pizzeria.model.Ingredient;

@Stateless
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class IngredientService {

	@PersistenceContext(unitName = "pizzeria-admin-web")
	private EntityManager em;

	private TypedQuery<Ingredient> query;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Ingredient> findAll() throws StockageException {
		try {
			this.query = this.em.createQuery("select i from Ingredient i", Ingredient.class);
			List<Ingredient> ingredients = this.query.getResultList();
			return ingredients;
		} catch (Exception e) {
			throw new StockageException("Erreur de récupération des ingredients", e);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Ingredient> findAllAvailable() throws StockageException {
		try {
			this.query = this.em.createQuery("select i from Ingredient i where i.archive=:archive", Ingredient.class);
			this.query.setParameter("archive", Boolean.FALSE);

			List<Ingredient> ingredients = this.query.getResultList();
			return ingredients;
		} catch (Exception e) {
			throw new StockageException("Erreur de récupération des ingredients", e);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Ingredient findById(Integer id) throws StockageException {
		try {
			this.query = this.em.createQuery("select i from Ingredient i where i.id=:id", Ingredient.class);
			this.query.setParameter("id", id);

			return this.query.getSingleResult();
		} catch (Exception e) {
			throw new StockageException("Erreur de récupération d'un ingredient", e);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Ingredient findByName(String name) {
		try {
			this.query = this.em.createQuery("select i from Ingredient i where i.nom=:codP", Ingredient.class);
			this.query.setParameter("codP", name);

			return this.query.getSingleResult();
		} catch (Exception e) {
			throw new StockageException("Erreur de récupération d'un ingredient", e);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void save(Ingredient ingredient) throws StockageException {
		try {
			this.em.persist(ingredient);
		} catch (Exception e) {
			throw new StockageException("Erreur à l'ajout d'un ingredient", e);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void update(Integer id, Ingredient ingredient) throws StockageException {
		try {
			Ingredient old = this.findById(id);

			if(old != null){
				ingredient.setId(old.getId());
				this.em.merge(ingredient);
			}
		} catch (Exception e) {
			throw new StockageException("Erreur à l'update d'un ingredient", e);
		}
	}

	/**
	 * change le champ archive à true
	 * @param id
	 * @throws StockageException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Integer id) throws StockageException {
		try {
			Ingredient ingredient = this.findById(id);
			if(ingredient != null){
				ingredient.setArchive(true);
				//em.remove(ingredient);
				this.em.merge(ingredient);
			}
		} catch (Exception e) {
			throw new StockageException("Erreur à la suppression d'un ingredient", e);
		}
	}

}
