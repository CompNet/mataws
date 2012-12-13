package tr.edu.gsu.sine.ext;

import java.net.URI;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyManager;

import tr.edu.gsu.sine.Sine;
import tr.edu.gsu.sine.ext.Trait.Category;

/**
 * Provides facilities for retrieving and comparing ontological concepts.
 */
public class OntologyUtils {

	/**
	 * 
	 */
	private static OWLOntologyManager manager = OWLManager
			.createOWLOntologyManager();

	/**
	 * Returns true if the source concept matches the target concept, according
	 * to the degree of match defined in the profile.
	 * 
	 * @param sourceURI
	 *            left-side concept
	 * @param targetURI
	 *            right-side concept
	 * @param profile
	 *            extraction profile defining the degree of match.
	 * @return true if concepts match according to the degree of match.
	 */
	public static boolean isMatching(String sourceURI, String targetURI,
			Profile profile) {
		try {
			// Get the degree of match to be tested.
			Trait dom = profile.getTrait(Category.MATCHING);

			// Only the same concepts have the same URI.
			if (dom == Trait.EXACT) {
				return sourceURI.equals(targetURI);
			}
			if (dom == Trait.FITIN && sourceURI.equals(targetURI)) {
				return true;
			}

			// Split concept URI into ontology URI and concept name.
			String sourceSplit[] = sourceURI.split("#");
			String targetSplit[] = targetURI.split("#");

			// Do not compare concepts defined in different ontologies.
			if (!sourceSplit[0].equals(targetSplit[0])) {
				return false;
			}

			// Load ontology from mirrored ontologies directory.
			OWLOntology ontology = manager
					.loadOntologyFromPhysicalURI(mirroredURIOf(sourceSplit[0]));

			// Get both owl classes.
			OWLDataFactory owldf = manager.getOWLDataFactory();
			OWLClass sourceClass = owldf.getOWLClass(mirroredURIOf(sourceURI));
			OWLClass targetClass = owldf.getOWLClass(mirroredURIOf(targetURI));

			// Test, according to degree of match.
			if (dom == Trait.PLUGIN || dom == Trait.FITIN) {
				return isSubClassOf(sourceClass, targetClass, ontology);
			}
			if (dom == Trait.SUBSUME) {
				return isSubClassOf(targetClass, sourceClass, ontology);
			}
		} catch (OWLOntologyCreationException e) {
			Sine.getLogger().severe(
					"Can not compare " + sourceURI + " and " + targetURI + ": "
							+ e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Returns an URI to the file mirroring the ontology of specified URI.
	 * 
	 * @param stringURI
	 *            original URI of the ontology
	 * @return URI based on the "file" protocol.
	 * @.TODO Accessing ontologies using the "file" protocol also requires to
	 *       modify URIs in name spaces and in ontologies themselves.
	 */
	private static URI mirroredURIOf(String stringURI) {
		return URI.create(stringURI);
		/*
		 * TODO: mirroring ontologies requires to also modify name spaces. //
		 * strip protocol String resource =
		 * stringURI.substring(stringURI.indexOf("://") + 3);
		 * 
		 * // concatenate to mirrored ontologies path String absPath =
		 * Path.ONTOLOGIES.getValue() + File.separator + resource;
		 * 
		 * // concatenate to the file protocol return URI.create("file:///" +
		 * absPath);
		 */
	}

	/**
	 * Returns true if source OWL class is a (direct or distant) sub-class of
	 * target OWL class, according to the specified ontology.
	 * 
	 * @param source
	 *            OWL class supposed to be a sub-class of target
	 * @param target
	 *            OWL class supposed to be a super-class of source
	 * @param ontology
	 *            ontology that features both source and target classes
	 * @return true if source is a sub-class of target according to ontology
	 */
	private static boolean isSubClassOf(OWLClass source, OWLClass target,
			OWLOntology ontology) {
		for (OWLDescription superDesc : source.getSuperClasses(ontology)) {
			if (!superDesc.isAnonymous()) {
				OWLClass superClass = superDesc.asOWLClass();
				if (target.equals(superClass)) {
					return true;
				}
				// Recursive call.
				// N.B. No need of loop check, since ontologies are acyclic.
				// TODO: might optimize this by not checking twice same classes.
				if (isSubClassOf(superClass, target, ontology)) {
					return true;
				}
			}
		}
		return false;
	}
}
