package JavaImpl;

import JavaBean.Entity.User;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;

public class UserTableMode implements TableModel {

    private ArrayList<User> list;

    public UserTableMode(ArrayList<User> list){
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if(columnIndex == 0){
            return "usr_id";
        }else  if(columnIndex == 1){
            return "username";
        }else  if(columnIndex == 2){
            return "password";
        }else if(columnIndex == 3){
            return "authority";
        }else{
            return "error!";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex!=0){
            return true;
        }
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = list.get(rowIndex);
        if(columnIndex == 0){
            return "" + user.getUsr_id();
        }else if(columnIndex == 1){
            return "" + user.getUsrname();
        }else  if(columnIndex == 2){
            return "" + user.getPassword();
        }else  if(columnIndex == 3){
            return "" + user.getAuthority();
        }else{
            return "error!";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
}
