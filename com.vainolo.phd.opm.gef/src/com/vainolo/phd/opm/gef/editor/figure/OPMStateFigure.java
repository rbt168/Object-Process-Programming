/*******************************************************************************
 * Copyright (c) 2012 Arieh 'Vainolo' Bibliowicz
 * You can use this code for educational purposes. For any other uses
 * please contact me: vainolo@gmail.com
 *******************************************************************************/

package com.vainolo.phd.opm.gef.editor.figure;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

/**
 * A figure representing an OPM State. A state is represented by a rountangle
 * (rounded rectangle).
 * 
 * @author vainolo
 * 
 */
public class OPMStateFigure extends Figure implements OPMNodeFigure, OPMNamedElementFigure {
  private final RoundedRectangle rectangle;
  private final RoundedRectangle innerRectangle;
  private ConnectionAnchor connectionAnchor;
  private final SmartLabelFigure smartLabel;
  private boolean valueState = false;

  public OPMStateFigure() {
    super();
    setLayoutManager(new XYLayout());
    smartLabel = new SmartLabelFigure(OPMFigureConstants.TEXT_WIDTH_TO_HEIGHT_RATIO);
    smartLabel.setForegroundColor(OPMFigureConstants.LABEL_COLOR);
    add(smartLabel);
    rectangle = new RoundedRectangle();
    rectangle.setAntialias(SWT.ON);
    rectangle.setCornerDimensions(new Dimension(20, 20));
    rectangle.setFill(false);
    rectangle.setForegroundColor(OPMFigureConstants.STATE_COLOR);
    rectangle.setLineWidth(OPMFigureConstants.ENTITY_BORDER_WIDTH);
    innerRectangle = new RoundedRectangle();
    innerRectangle.setCornerDimensions(new Dimension(20, 20));
    innerRectangle.setFill(false);
    innerRectangle.setForegroundColor(OPMFigureConstants.STATE_COLOR);
    innerRectangle.setLineWidth(OPMFigureConstants.ENTITY_BORDER_WIDTH);
    add(rectangle);
    add(innerRectangle);

  }

  public void setValueState(boolean valueState) {
    this.valueState = valueState;
  }

  /**
   * All connections to the figure use the same anchor: a {@link ChopboxAnchor}.
   * 
   * @return a {@link ChopboxAnchor} for the state.
   */
  private ConnectionAnchor getConnectionAnchor() {
    if(connectionAnchor == null) {
      connectionAnchor = new ChopboxAnchor(this);
    }
    return connectionAnchor;
  }

  @Override
  public ConnectionAnchor getSourceConnectionAnchor() {
    return getConnectionAnchor();
  }

  @Override
  public ConnectionAnchor getTargetConnectionAnchor() {
    return getConnectionAnchor();
  }

  @Override
  public Dimension getPreferredSize(int wHint, int hHint) {
    return smartLabel.calculateSize().expand(16, 10);
  }

  @Override
  protected void paintFigure(Graphics graphics) {
    Rectangle r = getBounds().getCopy();
    if(valueState) {
      rectangle.setLineStyle(SWT.LINE_DASH);
    } else {
      rectangle.setLineStyle(SWT.LINE_SOLID);
    }
    setConstraint(rectangle, new Rectangle(0, 0, r.width, r.height));
    setConstraint(innerRectangle, new Rectangle(3, 3, r.width - 6, r.height - 6));
    if(valueState) {
      innerRectangle.setVisible(true);
    } else {
      innerRectangle.setVisible(false);
    }
    setConstraint(smartLabel, new Rectangle(8, 5, r.width - 16, r.height - 10));
  }

  @Override
  public SmartLabelFigure getNameFigure() {
    return smartLabel;
  }
}
