# File generated by petrify 4.2 (compiled 15-Oct-03 at 3:06 PM)
# from <inj_SeqMix_opt_2_all_workcraft.g> on 19-Sep-10 at 12:17 AM
# CPU time: 0.18 sec --  Host: mech-desktop
#   0.02(trav)+0.09(init)+0.06(min)+0.01(regs)+0.00(irred)
# The original TS had (before/after minimization) 60/20 states
# Original STG:   6 places,  40 transitions,  67 arcs (  9 pt +  9 tp + 49 tt)
# Current STG:    2 places,  17 transitions,  22 arcs (  3 pt +  3 tp + 16 tt)
# It is a pure Unique-Choice Petri net
.model inj_SeqMix_opt_2_all_workcraft.g
.inputs  c1_activate_rq c2_out_ac
.outputs  c1_activate_ac c2_out_rq
.graph
c1_activate_ac+ c1_activate_rq-
c1_activate_rq- c1_activate_ac-
c1_activate_ac- c1_activate_rq+
c1_activate_rq+ p0 c2_out_ac-/3
c2_out_rq+ c2_out_ac+
c2_out_ac+ c2_out_rq-
c2_out_rq- c2_out_ac-/1
c2_out_ac- c2_out_rq+
c2_out_ac-/1 c1_activate_ac+
c2_out_rq-/1 c2_out_ac-
c2_out_ac+/1 c2_out_rq-/1
c2_out_rq+/1 c2_out_ac+/1
c2_out_ac-/2 c2_out_rq+/1
c2_out_rq-/2 p1
c2_out_ac+/2 c2_out_rq-/2
c2_out_rq+/2 c2_out_ac+/2
c2_out_ac-/3 p0 c2_out_ac-/2
p0 c2_out_rq+/2
p1 c2_out_ac-/2 c2_out_ac-/3
.marking { <c1_activate_ac-,c1_activate_rq+> }
.end