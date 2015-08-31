package org.workcraft.plugins.cpog.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.workcraft.Framework;
import org.workcraft.exceptions.OperationCancelledException;
import org.workcraft.gui.ToolboxPanel;
import org.workcraft.gui.graph.GraphEditorPanel;
import org.workcraft.plugins.cpog.VisualCPOG;
import org.workcraft.workspace.WorkspaceEntry;

public class ImportEventLog extends PGMinerTool {

	@Override
	public String getDisplayName() {
		return "Import event log";
	}

	@Override
	public File getInputFile(WorkspaceEntry we) throws OperationCancelledException {

		File eventLog;
		JFileChooser chooser = new JFileChooser();
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			 eventLog = chooser.getSelectedFile();
		        try {
		        	if (!eventLog.exists()) {
		        		throw new FileNotFoundException();
		        	}

		        } catch (FileNotFoundException e1) {
		            // TODO Auto-generated catch block
		            JOptionPane.showMessageDialog(null, e1.getMessage(),
		                    "File not found error", JOptionPane.ERROR_MESSAGE);
		        } catch (NullPointerException e2) {

		        }

		} else {
			throw new OperationCancelledException("Open operation cancelled by user.");
		}





        return eventLog;
	}

	public void run(WorkspaceEntry we) {

			try {
				File eventLog = getInputFile(we);
				VisualCPOG visualCpog = (VisualCPOG) we.getModelEntry().getVisualModel();

				Scanner k;

				k = new Scanner(eventLog);
				System.out.println("Event log input");
				int i = 0;
				while (k.hasNext()) {
					String line = k.nextLine();
					while (line.endsWith(" \n")) {
						line = line.replace(" \n", "\n");
					}
					while (line.endsWith(" ")) {
						line = line.substring(0, line.length() - 1);
					}
					line = line.replace(" ", " -> ");
					line = "t" + (i++) + " = " + line;
					System.out.println(line);

					final Framework framework = Framework.getInstance();
					final GraphEditorPanel editor = framework.getMainWindow().getCurrentEditor();
					final ToolboxPanel toolbox = editor.getToolBox();
					final CpogSelectionTool tool = toolbox.getToolInstance(CpogSelectionTool.class);
					tool.insertExpression(line, false, false, true, true);
				}
				k.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OperationCancelledException e) {

			}

	}




}
