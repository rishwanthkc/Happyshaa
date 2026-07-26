"use client";

import { useState, useEffect } from "react";
import AppLayout from "../../components/AppLayout";
import api from "../../lib/api";
import { motion, AnimatePresence } from "framer-motion";
import { Users, UserPlus, Phone, MessageSquare, Star, Trash2, Edit2, AlertTriangle } from "lucide-react";

interface Contact {
  contactId: string;
  name: string;
  phone: string;
  email?: string;
  relationshipLabel: string;
  isFavorite: boolean;
  isEmergency: boolean;
}

export default function ContactsPage() {
  const [contacts, setContacts] = useState<Contact[]>([]);
  const [loading, setLoading] = useState(false);

  // Modal Inputs
  const [showModal, setShowModal] = useState(false);
  const [editingId, setEditingId] = useState<string | null>(null);

  const [nameInput, setNameInput] = useState("");
  const [phoneInput, setPhoneInput] = useState("");
  const [emailInput, setEmailInput] = useState("");
  const [relationshipInput, setRelationshipInput] = useState("Friend");
  const [isFavoriteInput, setIsFavoriteInput] = useState(false);
  const [isEmergencyInput, setIsEmergencyInput] = useState(false);

  const fetchContacts = async () => {
    setLoading(true);
    try {
      const response = await api.get("/api/v1/contacts");
      const list = response.data.map((c: any) => ({
        contactId: c.contact_id,
        name: c.name,
        phone: c.phone,
        email: c.email || "",
        relationshipLabel: c.relationship_label || "Friend",
        isFavorite: c.is_favorite || false,
        isEmergency: c.is_emergency || false
      }));
      setContacts(list);
    } catch (e) {
      // Mock Fallback
      setContacts([
        { contactId: "c1", name: "Sarah (Therapist)", phone: "+15550199", relationshipLabel: "Therapist", isFavorite: true, isEmergency: true },
        { contactId: "c2", name: "David (Brother)", phone: "+15550244", relationshipLabel: "Brother", isFavorite: false, isEmergency: false }
      ]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchContacts();
  }, []);

  const handleSave = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!nameInput || !phoneInput) return;

    try {
      const payload = {
        name: nameInput,
        phone: phoneInput,
        email: emailInput || null,
        relationship_label: relationshipInput,
        is_favorite: isFavoriteInput,
        is_emergency: isEmergencyInput
      };

      if (editingId) {
        // Edit contact
        await api.post(`/api/v1/contacts`, { ...payload, contact_id: editingId });
      } else {
        // Add new
        await api.post("/api/v1/contacts", payload);
      }
      closeForm();
      fetchContacts();
    } catch (e) {
      alert("Error syncing contacts to Firestore.");
    }
  };

  const handleDelete = async (contactId: string) => {
    try {
      await api.delete(`/api/v1/contacts/${contactId}`);
      fetchContacts();
    } catch (e) {
      setContacts(contacts.filter(c => c.contactId !== contactId));
    }
  };

  const toggleFavorite = async (contact: Contact) => {
    try {
      await api.post("/api/v1/contacts/favorite", { contact_id: contact.contactId });
      fetchContacts();
    } catch (e) {
      setContacts(contacts.map(c => c.contactId === contact.contactId ? { ...c, isFavorite: !c.isFavorite } : c));
    }
  };

  const openAddForm = () => {
    setNameInput("");
    setPhoneInput("");
    setEmailInput("");
    setRelationshipInput("Friend");
    setIsFavoriteInput(false);
    setIsEmergencyInput(false);
    setEditingId(null);
    setShowModal(true);
  };

  const openEditForm = (contact: Contact) => {
    setNameInput(contact.name);
    setPhoneInput(contact.phone);
    setEmailInput(contact.email || "");
    setRelationshipInput(contact.relationshipLabel);
    setIsFavoriteInput(contact.isFavorite);
    setIsEmergencyInput(contact.isEmergency);
    setEditingId(contact.contactId);
    setShowModal(true);
  };

  const closeForm = () => {
    setShowModal(false);
    setEditingId(null);
  };

  return (
    <AppLayout>
      <div className="max-w-5xl mx-auto space-y-6">
        
        {/* Header */}
        <div className="flex justify-between items-center">
          <h2 className="text-xl font-bold flex items-center gap-2">
            <Users className="h-5 w-5 text-[#00f2fe]" />
            My Support Circle
          </h2>
          <button
            onClick={openAddForm}
            className="bg-gradient-to-r from-[#00f2fe] to-[#4facfe] text-black font-bold py-2 px-4 rounded-lg text-sm flex items-center gap-2 cursor-pointer"
          >
            <UserPlus className="h-4 w-4" /> Add Contact
          </button>
        </div>

        {/* Emergency Alert Banner */}
        {contacts.some(c => c.isEmergency) && (
          <div className="bg-red-500/10 border border-red-500/20 rounded-2xl p-4 flex items-center gap-4">
            <AlertTriangle className="h-8 w-8 text-red-500" />
            <div>
              <h4 className="text-red-400 font-bold text-sm">Emergency Support Hotlinks</h4>
              <p className="text-xs text-white/70 mt-0.5">Click call to reach your emergency contact circle instantly.</p>
            </div>
          </div>
        )}

        {/* Contacts Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {contacts.map((contact) => (
            <motion.div
              key={contact.contactId}
              layout
              className={`glass-panel rounded-2xl p-5 flex justify-between items-center ${contact.isEmergency ? "border-l-4 border-l-red-500" : ""}`}
            >
              <div className="flex items-center gap-4">
                <div className={`h-11 w-11 rounded-xl flex items-center justify-center font-extrabold text-base ${contact.isEmergency ? "bg-red-500/20 text-red-400" : "bg-white/5 text-white/80"}`}>
                  {contact.name.substring(0, 1).toUpperCase()}
                </div>
                <div>
                  <h4 className="font-bold text-sm text-white flex items-center gap-2">
                    {contact.name}
                    {contact.isFavorite && <Star className="h-3.5 w-3.5 text-yellow-400 fill-yellow-400" />}
                  </h4>
                  <p className="text-xs text-white/50">{contact.relationshipLabel} • {contact.phone}</p>
                </div>
              </div>

              {/* Action Buttons */}
              <div className="flex items-center gap-1">
                <a 
                  href={`tel:${contact.phone}`}
                  className="p-2 text-[#00f2fe] hover:bg-white/5 rounded-lg transition"
                >
                  <Phone className="h-4.5 w-4.5" />
                </a>
                <a 
                  href={`sms:${contact.phone}`}
                  className="p-2 text-yellow-400 hover:bg-white/5 rounded-lg transition"
                >
                  <MessageSquare className="h-4.5 w-4.5" />
                </a>
                <button 
                  onClick={() => openEditForm(contact)}
                  className="p-2 text-white/60 hover:text-white rounded-lg transition cursor-pointer"
                >
                  <Edit2 className="h-4.5 w-4.5" />
                </button>
                <button 
                  onClick={() => handleDelete(contact.contactId)}
                  className="p-2 text-red-400 hover:bg-white/5 rounded-lg transition cursor-pointer"
                >
                  <Trash2 className="h-4.5 w-4.5" />
                </button>
              </div>
            </motion.div>
          ))}
        </div>

        {/* Add/Edit Modal */}
        {showModal && (
          <div className="fixed inset-0 bg-black/60 backdrop-blur-sm z-50 flex items-center justify-center p-4">
            <div className="glass-panel rounded-2xl max-w-md w-full p-6 space-y-4">
              <h3 className="text-lg font-bold">{editingId ? "Edit Circle Contact" : "Add Support Contact"}</h3>

              <form onSubmit={handleSave} className="space-y-4">
                <div>
                  <label className="text-white/60 text-xs block mb-1">Full Name</label>
                  <input
                    type="text"
                    required
                    value={nameInput}
                    onChange={(e) => setNameInput(e.target.value)}
                    className="glass-input w-full p-3 text-sm"
                  />
                </div>

                <div>
                  <label className="text-white/60 text-xs block mb-1">Phone Number</label>
                  <input
                    type="text"
                    required
                    value={phoneInput}
                    onChange={(e) => setPhoneInput(e.target.value)}
                    className="glass-input w-full p-3 text-sm"
                  />
                </div>

                <div>
                  <label className="text-white/60 text-xs block mb-1">Email (Optional)</label>
                  <input
                    type="email"
                    value={emailInput}
                    onChange={(e) => setEmailInput(e.target.value)}
                    className="glass-input w-full p-3 text-sm"
                  />
                </div>

                <div>
                  <label className="text-white/60 text-xs block mb-1">Relationship</label>
                  <input
                    type="text"
                    value={relationshipInput}
                    onChange={(e) => setRelationshipInput(e.target.value)}
                    className="glass-input w-full p-3 text-sm"
                    placeholder="e.g. Friend, Therapist, Mother"
                  />
                </div>

                <div className="flex flex-col gap-2 pt-2">
                  <label className="flex items-center gap-2 text-sm text-white/80 cursor-pointer">
                    <input
                      type="checkbox"
                      checked={isFavoriteInput}
                      onChange={(e) => setIsFavoriteInput(e.target.checked)}
                      className="accent-[#00f2fe]"
                    />
                    Favorite Contact
                  </label>

                  <label className="flex items-center gap-2 text-sm text-white/80 cursor-pointer">
                    <input
                      type="checkbox"
                      checked={isEmergencyInput}
                      onChange={(e) => setIsEmergencyInput(e.target.checked)}
                      className="accent-red-500"
                    />
                    Emergency Contact
                  </label>
                </div>

                <div className="flex gap-3 justify-end pt-2">
                  <button 
                    type="button" 
                    onClick={closeForm}
                    className="text-white/60 text-sm hover:text-white px-4 cursor-pointer"
                  >
                    Cancel
                  </button>
                  <button
                    type="submit"
                    className="bg-gradient-to-r from-[#00f2fe] to-[#4facfe] text-black font-bold py-2.5 px-5 rounded-lg text-sm cursor-pointer"
                  >
                    Save Contact
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}

      </div>
    </AppLayout>
  );
}
