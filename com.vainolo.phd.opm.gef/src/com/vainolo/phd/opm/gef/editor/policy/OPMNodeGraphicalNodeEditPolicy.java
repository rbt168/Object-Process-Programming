package com.vainolo.phd.opm.gef.editor.policy;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import com.vainolo.phd.opm.gef.editor.command.OPMLinkCreateCommand;
import com.vainolo.phd.opm.gef.editor.command.OPMNodeCreateCommand;
import com.vainolo.phd.opm.gef.editor.part.OPMNodeEditPart;
import com.vainolo.phd.opm.gef.editor.part.OPMStructuralLinkAggregatorEditPart;
import com.vainolo.phd.opm.model.OPMLink;
import com.vainolo.phd.opm.model.OPMNode;
import com.vainolo.phd.opm.model.OPMObjectProcessDiagram;
import com.vainolo.phd.opm.model.OPMStructuralLinkAggregator;
import com.vainolo.phd.opm.model.OPMThing;

/**
 * Policy used to connect two nodes in the diagram.
 * Currently connections can only be created between two {@link OPMThing} instances. 
 * @author vainolo
 */
public class OPMNodeGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {
    
    private static final Dimension DEFAULT_AGGREGATOR_DIMENSION = new Dimension(30, 30);
    
    /**
     * Create a command used to begin connecting to nodes.
     * {@link OPMStructuralLinkAggregatorEditPart} nodes cannot be source nodes, therefore in this
     * case a {@link UnexecutableCommand} is returned.
     * @return a {@link Command} that contains the initial information neede to create a
     * connection between two nodes.
     */
	@Override protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
	    // We must return null and not the usual UnexecutableCommand because is we return a 
	    // non-null value the framework thinks that the link can be created from this host,
	    // something that we don't want to happen.
	    if(request.getSourceEditPart() instanceof OPMStructuralLinkAggregatorEditPart) {
	        return null;
	    }
	    
	    // TODO for testing purposes
	    if(request.getNewObject() instanceof OPMStructuralLinkAggregator) {
	        request.setStartCommand(new Command() {});
	        return request.getStartCommand();
	    }
	    
		OPMLinkCreateCommand result = new OPMLinkCreateCommand();
		result.setSource((OPMNode)getHost().getModel());
		result.setLink((OPMLink) request.getNewObject());
		result.setOPD(((OPMNode)getHost().getModel()).getOpd());
		request.setStartCommand(result);
		return result;
	}

	/**
	 * Retrieves the command created by 
	 * {@link OPMNodeGraphicalNodeEditPolicy#getConnectionCreateCommand(CreateConnectionRequest) getConnectionCreateCommand},
	 * and adds it information so that the command can be executed.
	 * {@link OPMStructuralLinkAggregatorEditPart} nodes cannot be source nodes, therefore in this
	 * case a {@link UnexecutableCommand} is returned.
	 * @return a {@link Command} that can be executed to create a connection between two nodes. 
	 */
    @Override protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
        // A null command must be returned (and not the usual UnexecutableCommand), 
        // otherwise GEF shows the used a symbol that the connection can be completed but 
        // when the used clicks it is not created.
        if(request.getStartCommand() == null ||
           request.getTargetEditPart() instanceof OPMStructuralLinkAggregatorEditPart) {
            return null;
        }
        
        // TODO for testing purposes
        if(request.getNewObject() instanceof OPMStructuralLinkAggregator) {
            OPMNodeCreateCommand command = new OPMNodeCreateCommand();
            command.setNode((OPMNode) request.getNewObject());
            command.setParent((OPMObjectProcessDiagram) getHost().getParent().getModel());

            // Calculate location of aggregator, between the source and target nodes.
            Rectangle sourceConstraints = ((OPMNode)request.getSourceEditPart().getModel()).getConstraints();
            Rectangle targetConstraints = ((OPMNode)request.getTargetEditPart().getModel()).getConstraints();
            Point sourceCenter = new Point(sourceConstraints.x+sourceConstraints.width/2, sourceConstraints.y+sourceConstraints.height/2);
            Point targetCenter = new Point(targetConstraints.x+targetConstraints.width/2, targetConstraints.y+targetConstraints.height/2);
            Point aggregatorLeftTopCorner = new Point();
            aggregatorLeftTopCorner.x = sourceCenter.x + (targetCenter.x-sourceCenter.x)/2 - DEFAULT_AGGREGATOR_DIMENSION.width/2;
            aggregatorLeftTopCorner.y = sourceCenter.y + (targetCenter.y-sourceCenter.y)/2 - DEFAULT_AGGREGATOR_DIMENSION.height/2;
            if(aggregatorLeftTopCorner.x < 0) {
                aggregatorLeftTopCorner.x = 0;
            }
            if(aggregatorLeftTopCorner.y < 0) {
                aggregatorLeftTopCorner.y = 0;
            }
            command.setConstraints(new Rectangle(aggregatorLeftTopCorner, DEFAULT_AGGREGATOR_DIMENSION));
            
            return command;
        }
        
        OPMLinkCreateCommand result = (OPMLinkCreateCommand) request.getStartCommand();
        result.setTarget((OPMNode)getHost().getModel());
        return result;
    }

	@Override protected Command getReconnectTargetCommand(ReconnectRequest request) {
		return null;
	}

	@Override protected Command getReconnectSourceCommand(ReconnectRequest request) {
		return null;
	}
}