package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.button.Button;


public class TriggerButton extends Button {
    XboxController xboxController;
    Hand hand;

    public TriggerButton(XboxController xboxController, Hand hand) {
        this.xboxController = xboxController;
        this.hand = hand;
    }

    
    public boolean get() {
        return xboxController.getTriggerAxis(hand) > 0.5;
    }



}