/* This code is copyrighted by Articulate Software (c) 2007.
It is released under the GNU Public License <http://www.gnu.org/copyleft/gpl.html>.
Users of this code also consent, by use of this code, to credit Articulate Software in any
writings, briefings, publications, presentations, or other representations of any
software which incorporates, builds on, or uses this code.
Please cite the following article in any publication with references:

Pease, A., (2003). The Sigma Ontology Development Environment, 
in Working Notes of the IJCAI-2003 Workshop on Ontology and Distributed Systems,
August 9, Acapulco, Mexico.  See also http://sigmakee.sourceforge.net
*/
package com.articulate.sigma;
import java.util.*;
import java.io.*;

/* A class to interface with databases and database-like formats,
such as spreadsheets. */

public class DB {

    /** ***************************************************************
     */
    private static String wordWrap(String input, int length) {

        StringBuffer s = new StringBuffer();
        String[] r = input.split("\n");
        if (length > 0) {        
            for (int i = 0; i < r.length; i++) {
                s = new StringBuffer();
                while (r[i].length() > length) {
                    int cursor = length;
                    while (cursor > 0 && r[i].charAt(cursor) != ' ') 
                        cursor--;
                    if (cursor > 0)  {
                        s.append(r[i].substring(0,cursor) +  System.getProperty("line.separator"));
                        r[i] = r[i].substring(cursor+1);
                    }
                }
                r[i] = s.toString() + r[i];
            }
        }

        s = new StringBuffer();
        for (int i = 0; i < r.length; i++) {
            s.append(r[i] +  System.getProperty("line.separator"));
        }
        return s.toString();
    };

    /** ***************************************************************
     */
    private static ArrayList readSpreadsheet() {

        ArrayList rows = new ArrayList();

        try {
            FileReader r = new FileReader("scowCCLI.csv");
            LineNumberReader lr = new LineNumberReader(r);
            String line = null;
            while ((line = lr.readLine()) != null) {
                StringBuffer cell = new StringBuffer();
                ArrayList row = new ArrayList();
                boolean inString = false;
                for (int i = 0; i < line.length(); i++) {
                    //System.out.print(line.charAt(i));
                    if (line.charAt(i) == ',' && !inString) {
                        row.add(cell.toString());
                        cell = new StringBuffer();
                    } else if (line.charAt(i) == '"') {
                        inString = !inString;
                        //System.out.println();
                        //System.out.println(inString);
                    } else  
                        cell.append(line.charAt(i));                    
                }
                rows.add(row);
            }
        }
        catch (IOException ioe) {
            System.out.println("Error in DB.readSpreadsheet(): IOException: " + ioe.getMessage());
        } 
        return rows;
    }      


    /** ***************************************************************
     */
    private static void processSpreadsheet(ArrayList rows) {

        String domain = "";
        String subject = "";
        String relator = "";
        String range = "";
        String cell = "";
        String documentation = "";
        for (int rowNum = 43; rowNum < rows.size(); rowNum++) {
            ArrayList row = (ArrayList) rows.get(rowNum);
            if (row.size() > 14) {                                              // documentation
                cell = (String) row.get(12);
                if (cell.indexOf("^") > -1) 
                    subject = cell.substring(cell.indexOf("^") + 1, cell.length());
                else 
                    subject = cell;
                documentation = (String) row.get(14);
                if (documentation != null && documentation != "" && documentation.length() > 0) {
                    String doc = "(localDocumentation CCLI EnglishLanguage " + subject + " \"" + documentation + "\")";
                    System.out.print(wordWrap(doc,70));
                }
                if (row.size() > 23) {                                          // other triples
                    cell = (String) row.get(21);
                    if (cell.indexOf("^") > -1) 
                        domain = cell.substring(cell.indexOf("^") + 1, cell.length());
                    else 
                        domain = cell;
                    cell = (String) row.get(23);
                    if (cell.indexOf("^") > -1) 
                        relator = cell.substring(cell.indexOf("^") + 1, cell.length());
                    else 
                        relator = cell;
                    if (relator.equals("IsA")) relator = "instance";
                    if (relator.equals("IsSubClassOf")) relator = "subclass";
                    if (relator.equals("IsSubRelatorOf")) relator = "subrelation";
                    if (relator.equals("IsReciprocalOf")) relator = "inverse";            
                    cell = (String) row.get(24);
                    if (cell.indexOf("^") > -1) 
                        range = cell.substring(cell.indexOf("^") + 1, cell.length());
                    else 
                        range = cell;
                    if (domain != null && domain != "" && domain.length() > 0)
                        System.out.println("(" + relator + " " + domain + " " + range + ")");            
                    if (row.size() > 28) {                                      // cardinality                                                                                
                        String cardinality = (String) row.get(23);
                        System.out.println("(cardinality" + cardinality);                        
                        if (cardinality.equals("0-1") || cardinality.equals("1")) 
                            System.out.println("(instance " + domain + " SingleValuedRelation)");                        
                        if (row.size() > 37) {                                      // synonymousExternalConcepts
                            cell = (String) row.get(38);
                            if (cell.indexOf("^") > -1) 
                                relator = cell.substring(cell.indexOf("^") + 1, cell.length());
                            else 
                                relator = cell;
                            cell = (String) row.get(39);
                            if (cell.indexOf("^") > -1) 
                                range = cell.substring(cell.indexOf("^") + 1, cell.length());
                            else 
                                range = cell.trim();
                            //System.out.println("INFO: " + range + " " + domain + " " + relator); 
                            if (domain != null && domain != "" && domain.length() > 0 &&
                                relator != null && relator != "" && relator.length() > 0 && relator.equals("IsSameAs"))
                                System.out.println("(synonymousExternalConcept \"" + range + "\" " + domain + " CCLI)"); 
                        }
                    }
                }
            }
        }
    }

    /** ***************************************************************
     *  Collect relations in the knowledge base 
     *
     *  @return The set of relations in the knowledge base.
     */
    private ArrayList getRelations(KB kb) {

        ArrayList relations = new ArrayList();
        Iterator it = kb.terms.iterator();
        while (it.hasNext()) {
            String term = (String) it.next();
            if (kb.childOf(term,"BinaryPredicate"))
                relations.add(term.intern());            
        }
        return relations;
    }      

    /** ***************************************************************
     * Print a comma-delimited matrix.  The values of the rows
     * are TreeMaps, whose values in turn are Strings.  The ArrayList of
     * relations forms the column headers, which are Strings.
     * 
     * @param rows - the matrix
     * @param relations - the relations that form the column header
     */
    private void printSpreadsheet(TreeMap rows, ArrayList relations) {

        StringBuffer line = new StringBuffer();
        line.append("Domain/Range,");
        for (int i = 0; i < relations.size(); i++) {
            String relation = (String) relations.get(i);
            line.append(relation);
            if (i < relations.size()-1)
                line.append(",");            
        }
        System.out.println(line);

        Iterator it = rows.keySet().iterator();
        while (it.hasNext()) {
            String term = (String) it.next();
            TreeMap row = (TreeMap) rows.get(term);
            System.out.print(term + ",");

            for (int i = 0; i < relations.size(); i++) {
                String relation = (String) relations.get(i);
                if (row.get(relation) == null) 
                    System.out.print(",");
                else {
                    System.out.print((String) row.get(relation));
                    if (i < relations.size()-1)
                        System.out.print(",");            
                }
                if (i == relations.size()-1)
                    System.out.println();
            }
        }
    }      

    /** ***************************************************************
     * Export a comma-delimited table of all the ground binary
     * statements in the knowledge base.  Only the relations that are
     * actually used are included in the header.
     *
     *  @param The knowledge base.
     */
    public void exportTable(KB kb) {

        ArrayList relations = getRelations(kb);
        ArrayList usedRelations = new ArrayList();
        TreeMap rows = new TreeMap();
        for (int i = 0; i < relations.size(); i++) {
            String term = (String) relations.get(i);
            ArrayList statements = kb.ask("arg",0,term);
            if (statements != null) {
                TreeMap row = new TreeMap();
                for (int j = 0; j < statements.size(); j++) {
                    Formula f = (Formula) statements.get(j);
                    String arg1 = f.getArgument(1);
                    if (Character.isUpperCase(arg1.charAt(0)) && !arg1.endsWith("Fn")) {
                        if (!usedRelations.contains(term)) 
                            usedRelations.add(term);
                        String arg2 = f.getArgument(2);
                        if (rows.get(f.getArgument(1)) == null) {
                            row = new TreeMap();                    
                            rows.put(arg1,row);
                        }
                        else 
                            row = (TreeMap) rows.get(arg1); 
                        if (row.get(term) == null) 
                            row.put(term,f.getArgument(2));
                        else {
                            String element = (String) row.get(term);
                            element = element + "/" + f.getArgument(2);
                            row.put(term,element);
                        }
                    }
                }
            }
        }
        printSpreadsheet(rows,usedRelations);
    }      

    /** *************************************************************
     * A test method.
     */
    public static void main (String args[]) {

    /*
        DB db = new DB();
        try {
            KBmanager.getMgr().initializeOnce();
        } catch (IOException ioe ) {
            System.out.println(ioe.getMessage());
        }
        KB kb = KBmanager.getMgr().getKB("SUMO");
        db.exportTable(kb);
        */
        ArrayList al = readSpreadsheet();
        processSpreadsheet(al);
    }
}
