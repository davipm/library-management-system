import Link from 'next/link';

type Params = {
  params: Promise<{ id: string }>;
};

export default async function Page({ params }: Params) {
  const { id } = await params;

  return (
    <div>
      <div>Books {id}</div>
      <Link href={`/dashboard/books/${id}/edit`}>Edit</Link>
    </div>
  );
}
