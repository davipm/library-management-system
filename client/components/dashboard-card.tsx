'use client';

import type { ReactNode } from 'react';
import { Card, CardContent, CardHeader } from '@/components/ui/card';
import { Skeleton } from '@/components/ui/skeleton';

type Props = {
  text: string;
  length: number;
  loading: boolean;
  icon?: ReactNode;
};

export function DashboardCard({ text, length, loading, icon }: Props) {
  if (loading) {
    return (
      <div className="flex w-full max-w-sm h-[175px] flex-col gap-3 rounded-lg border p-4">
        <div className="flex items-center gap-2">
          <Skeleton className="h-5 w-16 rounded-full" />
        </div>
        <Skeleton className="h-8 w-3/4 mt-auto" />
        <Skeleton className="h-3 w-5/6" />
      </div>
    );
  }

  return (
    <Card>
      <CardHeader>
        <div className="flex items-center">
          {icon}
          <h3 className="text-lg font-medium">{text}</h3>
        </div>
      </CardHeader>
      <CardContent>
        <div className="text-3xl font-bold">{length}</div>
        <p className="text-sm text-muted-foreground mt-2">Total {text} in library</p>
      </CardContent>
    </Card>
  );
}
