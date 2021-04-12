// Eashan Vytla
// 3/29/2020
// Purpose: This program allows the user to program a virtual robot to debug their code before deploying

package InternalFiles;

import javax.swing.*;
import java.util.ArrayList;

public class Telemetry {
    private int numItems;
    private ArrayList<String> caption = new ArrayList<>();
    private ArrayList<String> message = new ArrayList<>();
    private int index = 0;

    private boolean contains(String[] strarr, String inquiry){
        int i = 0;
        for (String item : strarr) {
            if(item.equals(inquiry)){
                return true;
            }
            i++;
        }
        return false;
    }

    public void addData(String caption, Object message)
    {
        if (!this.caption.contains(caption))
        {
            this.caption.add(caption);
        }

        for(int i = 0; i < this.caption.size(); i++)
        {
            if(this.caption.get(i).equals(caption))
            {
                index = i;
            }
        }
        if(index < this.message.size() - 1){
            this.message.set(index, message.toString());
        }else{
            this.message.add(message.toString());
        }
    }

    public void clear(){
        message.clear();
        caption.clear();
    }

    public void update(){
        return;
    }

    protected void updateInternal(JList list)
    {
        if(this.caption.size() > 0 && this.message.size() > 0){
            DefaultListModel demoList = new DefaultListModel();
            for(int i = 0; i < caption.size(); i++)
            {
                demoList.addElement(this.caption.get(i) + ": " + this.message.get(i));
            }
            list.setModel(demoList);
        }
    }
}
