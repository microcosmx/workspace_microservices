package rebook.globalValue;

import rebook.domain.GetTripAllDetailResult;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by fdse-jichao on 2017/7/29.
 */
public class GlobalValue {

    public static Queue<GetTripAllDetailResult> gtdrs = new LinkedList<GetTripAllDetailResult>();
    public static int count = 0;

    public static GetTripAllDetailResult getGtdr(){
        System.out.println("[Rebook Service][Global Value][Glocal Value: + " + count + " +] Poll.");
        return gtdrs.poll();
    }

    public static void offerGtdr(GetTripAllDetailResult gtdr){
        gtdrs.offer(gtdr);
        System.out.println("[Rebook Service][Rebook Service][Glocal Value: + " + count + " +] Offer");
    }

}
