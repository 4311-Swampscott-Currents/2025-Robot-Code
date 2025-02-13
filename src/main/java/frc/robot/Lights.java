package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class Lights extends RobotContainer {

  Spark blinkin = new Spark(0);
  XboxController controller = new XboxController(0);

  public void robotPeriodic() {
    if (controller.getAButton()) {
      blinkin.set(0.87);
    } else {
      blinkin.set(-0.99);
    }
  }
}

/*it feels wrong, but the way that they made the blinkin is that the PWM range is the same as a PWM Sparkmax,
so you can control it the same as a sparkmax. Link to the conversion chart:
https://1166281274-files.gitbook.io/~/files/v0/b/gitbook-x-prod.appspot.com/o/spaces%2F-ME3KPEhFI6-MDoP9nZD%2Fuploads%2FMOYJvZmWgxCVKJhcV5fn%2FREV-11-1105-LED-Patterns.pdf?alt=media&token=e8227890-6dd3-498d-834a-752fa43413fe */
