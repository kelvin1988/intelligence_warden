package org.spring.springboot.domain;

public enum CruiseTrackPolicyEnum {

    CT_POLICY_1(1),
    CT_POLICY_2(2);

    private int mPolicy = 0 ;

    private CruiseTrackPolicyEnum(int policy){
        mPolicy = policy;
    }

    public int getPolicy(){
        return mPolicy;
    }

    public void setPolicy(int policy) {
        mPolicy = policy;
    }
};
