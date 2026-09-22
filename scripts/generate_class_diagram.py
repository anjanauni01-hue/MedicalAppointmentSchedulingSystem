"""
Generates docs/class-diagram.png - a UML class diagram for the
Medical Appointment Scheduling System (SE1020 OOP project).

Run: python3 generate_class_diagram.py
"""
import matplotlib.pyplot as plt
from matplotlib.patches import FancyBboxPatch, FancyArrowPatch

FIG_W, FIG_H = 19, 17.5
fig, ax = plt.subplots(figsize=(FIG_W, FIG_H))
ax.set_xlim(0, FIG_W)
ax.set_ylim(0, FIG_H)
ax.axis('off')
ax.invert_yaxis()  # so (x, y) top-left anchors read naturally top-to-bottom

HEADER_ABSTRACT = "#5b3ea6"
HEADER_CONCRETE = "#0b5ed7"
HEADER_ENUM = "#b8860b"
BODY_COLOR = "#ffffff"
BORDER_COLOR = "#2b2b2b"
TEXT_COLOR = "#1a1a1a"

TITLE_H = 0.55
LINE_H = 0.30
PAD = 0.16


def draw_class_box(x_top, y_top, w, title, attrs, methods, abstract=False, is_enum=False):
    """x_top, y_top = TOP-LEFT corner. Box height is auto-computed from content."""
    header_color = HEADER_ENUM if is_enum else (HEADER_ABSTRACT if abstract else HEADER_CONCRETE)

    attr_h = max(len(attrs), 1) * LINE_H + PAD if attrs else PAD
    meth_h = max(len(methods), 1) * LINE_H + PAD if methods else PAD
    total_h = TITLE_H + attr_h + meth_h

    # outer box (note: y grows downward because axis is inverted)
    box = FancyBboxPatch((x_top, y_top), w, total_h, boxstyle="round,pad=0,rounding_size=0.05",
                          linewidth=1.4, edgecolor=BORDER_COLOR, facecolor=BODY_COLOR, zorder=2)
    ax.add_patch(box)

    # header
    header = FancyBboxPatch((x_top, y_top), w, TITLE_H, boxstyle="round,pad=0,rounding_size=0.05",
                             linewidth=1.4, edgecolor=BORDER_COLOR, facecolor=header_color, zorder=3)
    ax.add_patch(header)
    title_cy = y_top + TITLE_H / 2 - (0.09 if abstract else 0)
    ax.text(x_top + w / 2, title_cy, title, ha='center', va='center',
            fontsize=12.5, fontweight='bold', color='white',
            style='italic' if abstract else 'normal', zorder=4)
    if abstract:
        ax.text(x_top + w / 2, y_top + TITLE_H / 2 + 0.17, "«abstract»", ha='center', va='center',
                fontsize=7.5, color='#e6ddff', zorder=4)

    ax.plot([x_top, x_top + w], [y_top + TITLE_H, y_top + TITLE_H], color=BORDER_COLOR, linewidth=1.2, zorder=4)

    ay = y_top + TITLE_H + PAD / 2 + LINE_H / 2
    for a in attrs:
        ax.text(x_top + 0.15, ay, a, ha='left', va='center', fontsize=9, color=TEXT_COLOR, family='monospace', zorder=4)
        ay += LINE_H

    div2_y = y_top + TITLE_H + attr_h
    ax.plot([x_top, x_top + w], [div2_y, div2_y], color=BORDER_COLOR, linewidth=1.0, zorder=4)

    my = div2_y + PAD / 2 + LINE_H / 2
    for m in methods:
        ax.text(x_top + 0.15, my, m, ha='left', va='center', fontsize=8.8, color=TEXT_COLOR, family='monospace', zorder=4)
        my += LINE_H

    return {"x": x_top, "y": y_top, "w": w, "h": total_h,
            "top": (x_top + w / 2, y_top), "bottom": (x_top + w / 2, y_top + total_h),
            "left": (x_top, y_top + total_h / 2), "right": (x_top + w, y_top + total_h / 2)}


def inheritance_arrow(child_top_point, parent_bottom_point):
    arrow = FancyArrowPatch(child_top_point, parent_bottom_point, arrowstyle="-|>",
                             mutation_scale=24, linewidth=1.4, color=BORDER_COLOR,
                             facecolor='white', zorder=1, shrinkA=0, shrinkB=2)
    ax.add_patch(arrow)


def association_line(p1, p2, mult1="", mult2=""):
    ax.plot([p1[0], p2[0]], [p1[1], p2[1]], color="#555555", linewidth=1.1, zorder=1)
    if mult1:
        ax.text(p1[0] + 0.18, p1[1] - 0.05, mult1, fontsize=8, ha='left', va='center')
    if mult2:
        ax.text(p2[0] - 0.18, p2[1] - 0.05, mult2, fontsize=8, ha='right', va='center')


# ---------------------------------------------------------------
# Title
# ---------------------------------------------------------------
ax.text(FIG_W / 2, 0.35, "Medical Appointment Scheduling System — Class Diagram", ha='center', va='center',
        fontsize=18, fontweight='bold', color="#0b1f4d")
ax.text(FIG_W / 2, 0.75, "SE1020 Object Oriented Programming  |  Anjana (IT22195470)", ha='center', va='center',
        fontsize=10.5, color="#555555")

# ---------------------------------------------------------------
# PERSON HIERARCHY (top area)
# ---------------------------------------------------------------
person = draw_class_box(7.0, 1.25, 4.6,
                         "Person",
                         ["- id: Long", "- fullName: String", "- email: String",
                          "- phoneNumber: String", "- address: String",
                          "- createdAt: LocalDateTime"],
                         ["+ getRole(): String   {abstract}"],
                         abstract=True)

row2_y = person["bottom"][1] + 1.0
patient = draw_class_box(0.6, row2_y, 4.6, "Patient",
                          ["- dateOfBirth: LocalDate", "- gender: String",
                           "- bloodGroup: String", "- medicalHistory: String",
                           "- appointments: List<Appointment>"],
                          ["+ getRole(): String"])

doctor = draw_class_box(5.6, row2_y, 4.6, "Doctor",
                         ["- specialization: String", "- licenseNumber: String",
                          "- consultationFee: double", "- availableDays: String",
                          "- appointments: List<Appointment>"],
                         ["+ getRole(): String"])

admin = draw_class_box(10.6, row2_y, 4.0, "Admin",
                        ["- designation: String"],
                        ["+ getRole(): String"])

inheritance_arrow(patient["top"], (patient["top"][0], person["bottom"][1]))
inheritance_arrow(doctor["top"], (doctor["top"][0], person["bottom"][1]))
inheritance_arrow(admin["top"], (admin["top"][0], person["bottom"][1]))
ax.plot([patient["top"][0], admin["top"][0]], [person["bottom"][1] + 0.001, person["bottom"][1] + 0.001],
        color=BORDER_COLOR, linewidth=1.4, zorder=1)

# ---------------------------------------------------------------
# APPOINTMENT HIERARCHY (middle / bottom area)
# ---------------------------------------------------------------
appt_y = row2_y + patient["h"] + 1.1
appointment = draw_class_box(6.6, appt_y, 4.8, "Appointment",
                              ["- id: Long", "- patient: Patient", "- doctor: Doctor",
                               "- appointmentDate: LocalDate", "- appointmentTime: LocalTime",
                               "- status: AppointmentStatus", "- reasonForVisit: String"],
                              ["+ getModeDetails(): String   {abstract}",
                               "+ calculateFee(base): double   {abstract}"],
                              abstract=True)

enum_box = draw_class_box(13.0, appt_y + 0.2, 3.3, "AppointmentStatus",
                           [], ["PENDING", "CONFIRMED", "COMPLETED", "CANCELLED"], is_enum=True)

row4_y = appointment["bottom"][1] + 1.15
inperson = draw_class_box(3.4, row4_y, 4.4, "InPersonAppointment",
                           ["- roomNumber: String"],
                           ["+ getModeDetails(): String", "+ calculateFee(base): double"])

online = draw_class_box(8.4, row4_y, 4.4, "OnlineAppointment",
                         ["- meetingLink: String"],
                         ["+ getModeDetails(): String", "+ calculateFee(base): double"])

inheritance_arrow(inperson["top"], (inperson["top"][0], appointment["bottom"][1]))
inheritance_arrow(online["top"], (online["top"][0], appointment["bottom"][1]))
ax.plot([inperson["top"][0], online["top"][0]], [appointment["bottom"][1] + 0.001, appointment["bottom"][1] + 0.001],
        color=BORDER_COLOR, linewidth=1.4, zorder=1)

# ---------------------------------------------------------------
# ASSOCIATIONS
# ---------------------------------------------------------------
association_line(patient["bottom"], (appointment["x"] + 0.6, appointment["y"]), mult1="1", mult2="0..*")
association_line(doctor["bottom"], (appointment["x"] + appointment["w"] - 0.6, appointment["y"]), mult1="1", mult2="0..*")
association_line((appointment["x"] + appointment["w"], appointment["y"] + 0.9),
                  (enum_box["x"], enum_box["y"] + 0.5))

# ---------------------------------------------------------------
# Legend
# ---------------------------------------------------------------
lx, ly = 0.6, row4_y + inperson["h"] + 0.6
ax.add_patch(FancyArrowPatch((lx, ly), (lx + 1.0, ly), arrowstyle="-|>", mutation_scale=20,
                              linewidth=1.4, color=BORDER_COLOR, facecolor='white'))
ax.text(lx + 1.2, ly, "Inheritance (extends)", fontsize=9.5, va='center')
ax.plot([lx, lx + 1.0], [ly + 0.4, ly + 0.4], color="#555555", linewidth=1.1)
ax.text(lx + 1.2, ly + 0.4, "Association (uses / has)", fontsize=9.5, va='center')

fig.subplots_adjust(left=0.01, right=0.99, top=0.99, bottom=0.01)
plt.savefig("/home/claude/MedicalAppointmentSchedulingSystem/docs/class-diagram.png", dpi=190,
            bbox_inches='tight', facecolor='white')
print("Saved docs/class-diagram.png")
