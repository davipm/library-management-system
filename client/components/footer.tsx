import Link from 'next/link';

export function Footer() {
  return (
    <footer className="bg-white border-t">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
        <div className="md:flex md:items-center md:justify-between">
          <div className="flex justify-center md:justify-start">
            <p className="text-sm text-gray-500">
              &copy; {new Date().getFullYear()} Library Management System. All rights reserved.
            </p>
          </div>
          <div className="mt-4 md:mt-0 flex justify-center md:justify-end space-x-6">
            <Link href="#" className="text-gray-400 hover:text-gray-500">
              <span className="sr-only">Privacy Policy</span>
              Privacy
            </Link>
            <Link href="#" className="text-gray-400 hover:text-gray-500">
              <span className="sr-only">Terms</span>
              Terms
            </Link>
          </div>
        </div>
      </div>
    </footer>
  );
}
