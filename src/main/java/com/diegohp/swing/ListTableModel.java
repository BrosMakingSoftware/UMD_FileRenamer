
package com.diegohp.swing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * This class defines a {@link TableModel} based on a {@link List} as container.
 * 
 * @param <O> Class that defines the type of the container list.
 * 
 * @author diegohp (Diego Hernandez Perez) - <ahref="mailto:hp.diego@gmail.com">hp.diego@gmail.com</a>
 * @version 1.0
 */
public abstract class ListTableModel<O> extends AbstractTableModel {

    protected List<String> columnNames;
    protected List<O> objects;

    public ListTableModel() {
        this.columnNames = new ArrayList<String>();
        this.objects = new ArrayList<O>();
    }

    public ListTableModel(List<O> objects) {
        this.objects.addAll(objects);
    }

    public void addObject(O object) {
        this.objects.add(object);
        this.fireTableDataChanged();
    }

    public void addObjectList(List<O> objects) {
        this.objects.addAll(objects);
        this.fireTableDataChanged();
    }

    public O getObjectAt(int row) {
        return this.objects.get(row);
    }

    public O removeObjectAt(int row) {
        O object = this.objects.remove(row);
        this.fireTableDataChanged();
        return object;
    }
    
    public void removeAllObjects() {
        this.objects.clear();
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return this.objects.size();
    }

    @Override
    public int getColumnCount() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return this.columnNames.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames.get(col);
    }
    
    public void setColumnNames(List<String> columnNames){
        this.columnNames.addAll(columnNames);
    }
    
    public List<O> getObjects(){
        return this.objects;
    }

    @Override
    public abstract Object getValueAt(int row, int col);
}
