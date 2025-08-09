// apiService.ts
import axiosInstance from "./axiosInstance";

type HttpMethod = "GET" | "POST" | "PUT" | "DELETE";

interface CallApiOptions<T> {
  endpoint: string;
  method?: HttpMethod;
  body?: T;
  params?: Record<string, string | number>;
}

async function callApi<T = unknown, B = unknown>({
  endpoint,
  method = "GET",
  body,
  params,
}: CallApiOptions<B>): Promise<T> {
  const queryString = params ? `?${new URLSearchParams(params as any).toString()}` : "";
  const url = `${endpoint}${queryString}`;

  try {
    const response = await axiosInstance({
      method,
      url,
      headers: {
        "Content-Type": "application/json",
      },
      data: body ? JSON.stringify(body) : null,
    });

    return response.data as T;
  } catch (error) {
    console.error("API call error:", error);
    throw error;
  }
}

// Các hàm tiện ích
export function GET_ALL<T>(endpoint: string): Promise<T> {
  return callApi<T>({ endpoint, method: "GET" });
}

export function GET_ID<T>(endpoint: string): Promise<T> {
  return callApi<T>({ endpoint, method: "GET" });
}

export function LOGIN<T, B>(endpoint: string, body: B, params?: Record<string, string | number>): Promise<T> {
  return callApi<T, B>({ endpoint, method: "POST", body, params });
}

export function REGISTER<T, B>(endpoint: string, body: B): Promise<T> {
  return callApi<T, B>({ endpoint, method: "POST", body });
}

export function DELETE<T>(endpoint: string, params?: Record<string, string | number>): Promise<T> {
  return callApi<T>({ endpoint, method: "DELETE", params });
}
