import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

/** This class may be used to contain the semantic information such as
 * the inheritance graph.  You may use it or not as you like: it is only
 * here to provide a container for the supplied methods.  */
class ClassTable {
    private int semantErrors;
    private PrintStream errorStream;
	private Classes cls_with_basics;

    /** Creates data structures representing basic Cool classes (Object,
     * IO, Int, Bool, String).  Please note: as is this method does not
     * do anything useful; you will need to edit it to make if do what
     * you want.
     * */
    private void installBasicClasses() {
	AbstractSymbol filename 
	    = AbstractTable.stringtable.addString("<basic class>");
	
	// The following demonstrates how to create dummy parse trees to
	// refer to basic Cool classes.  There's no need for method
	// bodies -- these are already built into the runtime system.

	// IMPORTANT: The results of the following expressions are
	// stored in local variables.  You will want to do something
	// with those variables at the end of this method to make this
	// code meaningful.

	// The Object class has no parent class. Its methods are
	//        cool_abort() : Object    aborts the program
	//        type_name() : Str        returns a string representation 
	//                                 of class name
	//        copy() : SELF_TYPE       returns a copy of the object

	class_c Object_class = 
	    new class_c(0, 
		       TreeConstants.Object_, 
		       TreeConstants.No_class,
		       new Features(0)
			   .appendElement(new method(0, 
					      TreeConstants.cool_abort, 
					      new Formals(0), 
					      TreeConstants.Object_, 
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.type_name,
					      new Formals(0),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.copy,
					      new Formals(0),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0))),
		       filename);
	
	// The IO class inherits from Object. Its methods are
	//        out_string(Str) : SELF_TYPE  writes a string to the output
	//        out_int(Int) : SELF_TYPE      "    an int    "  "     "
	//        in_string() : Str            reads a string from the input
	//        in_int() : Int                "   an int     "  "     "

	class_c IO_class = 
	    new class_c(0,
		       TreeConstants.IO,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new method(0,
					      TreeConstants.out_string,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Str)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.out_int,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Int)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.in_string,
					      new Formals(0),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.in_int,
					      new Formals(0),
					      TreeConstants.Int,
					      new no_expr(0))),
		       filename);

	// The Int class has no methods and only a single attribute, the
	// "val" for the integer.

	class_c Int_class = 
	    new class_c(0,
		       TreeConstants.Int,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	// Bool also has only the "val" slot.
	class_c Bool_class = 
	    new class_c(0,
		       TreeConstants.Bool,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	// The class Str has a number of slots and operations:
	//       val                              the length of the string
	//       str_field                        the string itself
	//       length() : Int                   returns length of the string
	//       concat(arg: Str) : Str           performs string concatenation
	//       substr(arg: Int, arg2: Int): Str substring selection

	class_c Str_class =
	    new class_c(0,
		       TreeConstants.Str,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.Int,
					    new no_expr(0)))
			   .appendElement(new attr(0,
					    TreeConstants.str_field,
					    TreeConstants.prim_slot,
					    new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.length,
					      new Formals(0),
					      TreeConstants.Int,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.concat,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg, 
								     TreeConstants.Str)),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.substr,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Int))
						  .appendElement(new formalc(0,
								     TreeConstants.arg2,
								     TreeConstants.Int)),
					      TreeConstants.Str,
					      new no_expr(0))),
		       filename);

	/* Do somethind with Object_class, IO_class, Int_class,
           Bool_class, and Str_class here */
		   cls_with_basics.addElement(Object_class);
		   cls_with_basics.addElement(IO_class);
		   cls_with_basics.addElement(Int_class);
		   cls_with_basics.addElement(Bool_class);
		   cls_with_basics.addElement(Str_class);


    }

	// node class to make an inheritance tree
	class herit_node {
		AbstractSymbol class_name;
		AbstractSymbol parent_name;
		herit_node parent;
		ArrayList<herit_node> children;

		herit_node(AbstractSymbol name,AbstractSymbol parent_name) {
			class_name=name;
			this.parent_name=parent_name;
		}
	}
	


    public ClassTable(Classes cls) {
	semantErrors = 0;
	errorStream = System.err;
	
	/* fill this in */
		System.out.println("Hey, it's me!");

		// initialize a Classes that will include basic classes
		cls_with_basics=new Classes(0);
		// add the basic classes
		installBasicClasses();

		// get the list of classes
		Enumeration cls_e = cls.getElements();
		while( cls_e.hasMoreElements() ) {
			class_c c = (class_c) cls_e.nextElement();
			cls_with_basics.addElement(c);
		}

		// make a dictionnary of inheritance tree nodes to be able to get node by name
		HashMap<AbstractSymbol,herit_node> herit_node_by_name = new HashMap<AbstractSymbol,herit_node>();

		Enumeration cls_with_basics_e = cls_with_basics.getElements();
		while( cls_with_basics_e.hasMoreElements() ) {
			class_c c = (class_c) cls_with_basics_e.nextElement();
			System.out.println("Class "+c.name+" parent is "+c.parent);
			herit_node hn = new herit_node(c.name, c.parent);

			if( herit_node_by_name.containsKey(c.name) ) {
				System.out.println("Class "+c.name+" defined twice.");
				semantError(c);
			}
			else
			{
				herit_node_by_name.put(c.name,hn);
			}

		}

		// Fill in parent node of each herit_node
		// and checks that parent exists
		for( herit_node hn : herit_node_by_name.values() ) {
			if( herit_node_by_name.containsKey(hn.parent_name)) {
				hn.parent=herit_node_by_name.get(hn.parent_name);
			}
			else
			{
				if( hn.class_name == TreeConstants.Object_ ) {
					System.out.println("Ok, "+hn.class_name+" is Object.");
				}
				else
				{
					System.out.println("Class "+hn.class_name+" parent does not exist.");
					semantError();
				}
			}
		}

		// check for cycles
		// We'll use this dictionary to track already visited nodes
		HashMap<AbstractSymbol,Boolean> visited_by_name = new HashMap();
		for( AbstractSymbol cn : herit_node_by_name.keySet() ) {
			// initialize the dictionary of visited nodes to false
			for( AbstractSymbol _cn : herit_node_by_name.keySet() ) { visited_by_name.put(_cn,Boolean.FALSE); }
			visited_by_name.put(cn,Boolean.TRUE);

			AbstractSymbol _cn=cn;
			Boolean cycle_detected=Boolean.FALSE;

			while ( (herit_node_by_name.get(_cn).class_name != TreeConstants.Object_ ) & ( cycle_detected == Boolean.FALSE ) ) {
				AbstractSymbol pn=herit_node_by_name.get(_cn).parent_name;
				if( visited_by_name.get(pn) == Boolean.TRUE ) {
					System.out.println("Class "+pn+", parent of "+_cn+", has already been visited during cycle check!");
					cycle_detected=Boolean.TRUE;
					semantError();
				}
				else
				{
					_cn=herit_node_by_name.get(_cn).parent_name;
				}
			}

			
		}



    }

    /** Prints line number and file name of the given class.
     *
     * Also increments semantic error count.
     *
     * @param c the class
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(class_c c) {
	return semantError(c.getFilename(), c);
    }

    /** Prints the file name and the line number of the given tree node.
     *
     * Also increments semantic error count.
     *
     * @param filename the file name
     * @param t the tree node
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(AbstractSymbol filename, TreeNode t) {
	errorStream.print(filename + ":" + t.getLineNumber() + ": ");
	return semantError();
    }

    /** Increments semantic error count and returns the print stream for
     * error messages.
     *
     * @return a print stream to which the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError() {
	semantErrors++;
	return errorStream;
    }

    /** Returns true if there are any static semantic errors. */
    public boolean errors() {
	return semantErrors != 0;
    }
}
			  
    
