import { Phone, MessageCircle } from 'lucide-react'

export default function ContactFloat() {
  const phoneNumber = '0901234567'

  return (
    <div className="fixed bottom-6 right-6 flex flex-col gap-3 z-50">

      {/* 📞 GỌI ĐIỆN */}
      <a
        href={`tel:${phoneNumber}`}
        className="bg-green-500 hover:bg-green-600 text-white p-3 rounded-full shadow-lg transition flex items-center justify-center"
        title="Gọi ngay"
      >
        <Phone size={20} />
      </a>

      {/* 💬 ZALO */}
      <a
        href={`https://zalo.me/g/wjoile294`}
        target="_blank"
        rel="noopener noreferrer"
        className="bg-blue-500 hover:bg-blue-600 text-white p-3 rounded-full shadow-lg transition flex items-center justify-center"
        title="Chat Zalo"
      >
        <MessageCircle size={20} />
      </a>

    </div>
  )
}

