;;;the robots have just to move to another room
(load-goal '(and (in-room robot1 rram) (in-room robot2 rram)))

(load-start-state
 '((is-type rpdp room)
   (is-type rclk room)
   (is-type rram room)
   (is-type drclkrpdp door)
   (is-type drramrclk door)
   (is-type robot1 robot)
   (is-type robot2 robot)
   (connects drramrclk rclk rram)
   (connects drramrclk rram rclk)
   (connects drclkrpdp rclk rpdp)
   (connects drclkrpdp rpdp rclk)
   (statis drramrclk open)
   (statis drclkrpdp open)
   (in-room robot1 rclk)
   (in-room robot2 rclk)))
