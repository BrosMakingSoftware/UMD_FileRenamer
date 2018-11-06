
package com.diegohp.swing;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.MutableComboBoxModel;

/**
 * This class implements a {@link ComboBoxModel} based on a {@link List} as container.
 * 
 * @param <O> Class that defines the type of the container list.
 * 
 * @author diegohp (Diego Hernandez Perez) - <ahref="mailto:hp.diego@gmail.com">hp.diego@gmail.com</a>
 * @version 1.0
 */
public final class ListComboBoxModel<O> extends AbstractListModel implements MutableComboBoxModel, Serializable {
    
    private List<O> objects;
    private O selectedObject;

    /**
     * Constructs an empty DefaultComboBoxModel object.
     */
    public ListComboBoxModel() {
        this.objects = new ArrayList<O>();
    }


    /**
     * Constructs a DefaultComboBoxModel object initialized with
     * a List.
     * 
     */
    public ListComboBoxModel(java.util.List<O> list) {
        this.objects.addAll(list);

        if ( getSize() > 0 ) {
            selectedObject = getElementAt( 0 );
        }
    }

    // implements javax.swing.ComboBoxModel
    /**
     * Set the value of the selected item. The selected item may be null.
     * <p>
     * @param anObject The combo box value or null for no selection.
     */
    @Override
    public void setSelectedItem(Object anObject) {
        if ((selectedObject != null && !selectedObject.equals( anObject )) ||
	    selectedObject == null && anObject != null) {
	    selectedObject = (O)anObject;
	    fireContentsChanged(this, -1, -1);
        }
    }

    // implements javax.swing.ComboBoxModel
    @Override
    public Object getSelectedItem() {
        return selectedObject;
    }

    // implements javax.swing.ListModel
    @Override
    public int getSize() {
        return objects.size();
    }

    // implements javax.swing.ListModel
    @Override
    public O getElementAt(int index) {
        if ( index >= 0 && index < objects.size() )
            return objects.get(index);
        else
            return null;
    }

    /**
     * Returns the index-position of the specified object in the list.
     *
     * @param anObject  
     * @return an integer representing the index position, where 0 is 
     *         the first position
     */
    public int getIndexOf(Object anObject) {
        return objects.indexOf(anObject);
    }

    // implements javax.swing.MutableComboBoxModel
    @Override
    @SuppressWarnings("unchecked")
    public void addElement(Object anObject) {
        objects.add((O)anObject);
        fireIntervalAdded(this,objects.size()-1, objects.size()-1);
        if ( objects.size() == 1 && selectedObject == null && anObject != null ) {
            setSelectedItem( anObject );
        }
    }

    // implements javax.swing.MutableComboBoxModel
    @Override
    @SuppressWarnings("unchecked")
    public void insertElementAt(Object anObject,int index) {
        objects.add(index, (O)anObject);
        fireIntervalAdded(this, index, index);
    }

    // implements javax.swing.MutableComboBoxModel
    @Override
    public void removeElementAt(int index) {
        if ( getElementAt( index ) == selectedObject ) {
            if ( index == 0 ) {
                setSelectedItem( getSize() == 1 ? null : getElementAt( index + 1 ) );
            }
            else {
                setSelectedItem( getElementAt( index - 1 ) );
            }
        }

        objects.remove(index);

        fireIntervalRemoved(this, index, index);
    }

    // implements javax.swing.MutableComboBoxModel
    @Override
    public void removeElement(Object anObject) {
        int index = objects.indexOf(anObject);
        if ( index != -1 ) {
            removeElementAt(index);
        }
    }

    /**
     * Empties the list.
     */
    public void removeAllElements() {
        if ( objects.size() > 0 ) {
            int firstIndex = 0;
            int lastIndex = objects.size() - 1;
            objects.clear();
	    selectedObject = null;
            fireIntervalRemoved(this, firstIndex, lastIndex);
        } else {
	    selectedObject = null;
	}
    }
    
    public List<O> returnObjects(){
        return this.objects;
    }
}
